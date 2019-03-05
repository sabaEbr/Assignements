package main


import( "net/http"
		"fmt"
		"strings"
		"encoding/json"
		"math/rand"
		"strconv"
)

// Serve Error := 715 -> string to uint conversion error
// Serve Error := 815 -> json encryption error
// Serve Error := 816 -> json description error
// Serve Error := 915 -> Invalid game ID
 
type GameSesh struct{
    GameId uint32 `json:GameId`
	NPlayers uint8 `json:NPlayers`
	Turn uint8 `json:Turn`
    TurnSkip bool `json:TurnSkip`
	Status uint8 `json:Status`
	MessageT string `json:MessageT`
}
 

type DataStructPrototype struct{
    Games []GameSesh
}

func validGameID(dataPtr *DataStructPrototype, ID_u32 uint32) bool{
	for _, game := range (*dataPtr).Games{
		if ID_u32 == game.GameId{
			return true
		}
	}
	return false	
}

func newgame(dataPtr *DataStructPrototype) string{
	var newGame *GameSesh
	gameFound := false

	// Search for one player games
	for i:=0; i<len((*dataPtr).Games); i++ {
		if (*dataPtr).Games[i].NPlayers == 1{
			(*dataPtr).Games[i].NPlayers = 2
			(*dataPtr).Games[i].Status = 0
			newGame = &(*dataPtr).Games[i]
			// Indicate game was found
			gameFound = true
			break
		}
	}

	if gameFound == false{
		// If no games with one player found create new one
		validID := false
		var ID uint32
		for !validID {
			validID = true
			ID = rand.Uint32()
			for _, game := range (*dataPtr).Games{
				if ID == game.GameId{
					validID = false
					break
				}
			}
		}
		newGame = &(GameSesh{GameId: ID, NPlayers: 1, Turn: 1, Status: 2, MessageT: ""})
		(*dataPtr).Games = append((*dataPtr).Games, *newGame)
	}
	
	m, err := json.Marshal(newGame)
	if err != nil{
		return "Serve Error := 815"
	}
	return string(m)
}

func getgameinfo(dataPtr * DataStructPrototype, ID_s string) string {
	var getgame *GameSesh
	ID_u64, err := strconv.ParseUint(ID_s, 10, 32)

	if err != nil{
		return "Serve Error := 715\nGameID (" + ID_s +") must be unsigned interger"
	}

	ID_u32 := uint32(ID_u64)
	for _, game := range (*dataPtr).Games{
		if ID_u32 == game.GameId{
			getgame = &game
			break
		}
	}

	if getgame == nil {
		return "gameid (" + ID_s +") not found"
	} else {
		m, err := json.Marshal(getgame)
		if err != nil{
			return "Serve Error := 815"
		}
		return string(m)
	}
}

func postgameinfo(dataPtr * DataStructPrototype, rawInfo string) string {
	gameUD := new(GameSesh)
	err := json.Unmarshal(json.RawMessage(rawInfo), gameUD)

	if err != nil{
		return "Serve Error := 816\n Invalid Game Structure (" + rawInfo + ")"
	}

	for i:=0; i<len((*dataPtr).Games); i++{
		if (*dataPtr).Games[i].GameId == (*gameUD).GameId{
			(*dataPtr).Games[i] = *gameUD

			m, err := json.Marshal(gameUD)
			if err != nil{
				return "Serve Error := 815"
			}
			return string(m)
		}
	}
	return "Serve Error := 915"
}
 
func terminategame(dataPtr * DataStructPrototype, rawInfo string) string {
	gameUD := new(GameSesh)
	err := json.Unmarshal(json.RawMessage(rawInfo), gameUD)

	if err != nil{
		return "Serve Error := 816\n Invalid Game Structure (" + rawInfo + ")"
	}

	for i:=0; i<len((*dataPtr).Games); i++{
		if (*dataPtr).Games[i].GameId == (*gameUD).GameId{
			(*dataPtr).Games[i] = *gameUD
			if (*dataPtr).Games[i].NPlayers != 0{
				// Avoid breaking Nplayers
				(*dataPtr).Games[i].NPlayers--
			}
			(*dataPtr).Games[i].Status = 1

			m, err := json.Marshal(gameUD)
			if err != nil{
				return "Serve Error := 815"
			}
			return string(m)
		}
	}
	return "Serve Error := 915"

}

func (dataPtr *DataStructPrototype) ServeHTTP(w http.ResponseWriter, r *http.Request){

	request := strings.SplitN(r.URL.Path[1:],"/", 2)

	switch request[0]{
	case "new":
		fmt.Fprintf(w, "new game request\n")
		answer := newgame(dataPtr)
		fmt.Fprintf(w, answer)
		break
	case "terminate":
		fmt.Fprintf(w, "terminate game request\n")
		answer := terminategame(dataPtr, request[1])
		fmt.Fprintf(w, answer)
		break
	case "get":
		fmt.Fprintf(w, "get game info request\n")
		answer := getgameinfo(dataPtr, request[1])
		fmt.Fprintf(w, answer)
		break
	case "post":
		fmt.Fprintf(w, "post game info request\n")
		answer := postgameinfo(dataPtr, request[1])
		fmt.Fprintf(w, answer)
		break
	default:
		w.WriteHeader(404)
		w.Write([]byte("404 biatch - " + http.StatusText(404)))
	}
	
}

 

func main() {
	http.Handle("/", new(DataStructPrototype))
    http.ListenAndServe(":20020", nil)
}
