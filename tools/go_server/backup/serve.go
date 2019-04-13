package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"math/rand"
	"net/http"
	"strconv"
	"strings"
)

// Serve Error := 715 -> string to uint conversion error
// Serve Error := 815 -> json encryption error
// Serve Error := 816 -> json description error
// Serve Error := 915 -> Invalid game ID
// Serve Error := 925 -> Invalid number of cells

type GameSesh struct {
	GameId   uint32 `json:"ID"`
	NCells   uint8  `json:"NC"`
	NPlayers uint8  `json:"NP"`
	Turn     uint8  `json:"TR"`
	TurnSkip bool   `json:"TS"`
	Status   uint8  `json:"SS"`
	MessageT string `json:"MT"`
}

type DataStructPrototype struct {
	Games9  []*GameSesh
	Games13 []*GameSesh
	Games19 []*GameSesh
}

func (dataPtr *DataStructPrototype) removeFromSlice(nCells uint8, i int) {
	// Remove element i from dataStruct by pointer to update without return value
	switch nCells {

	case 9:
		(*dataPtr).Games9 = append((*dataPtr).Games9[:i], (*dataPtr).Games9[i+1:]...)
	case 13:
		(*dataPtr).Games13 = append((*dataPtr).Games13[:i], (*dataPtr).Games13[i+1:]...)
	case 19:
		(*dataPtr).Games19 = append((*dataPtr).Games19[:i], (*dataPtr).Games19[i+1:]...)

	}
}

func (dataPtr *DataStructPrototype) getSlice(nCells uint8) ([]*GameSesh, error) {
	switch nCells {

	case 9:
		return (*dataPtr).Games9, nil
	case 13:
		return (*dataPtr).Games13, nil
	case 19:
		return (*dataPtr).Games19, nil
	default:
		return nil, errors.New("Invalid Cells number")

	}
}

func (dataPtr *DataStructPrototype) appendToSlice(game *GameSesh) {
	switch (*game).NCells {

	case 9:
		(*dataPtr).Games9 = append((*dataPtr).Games9, game)
	case 13:
		(*dataPtr).Games13 = append((*dataPtr).Games13, game)
	case 19:
		(*dataPtr).Games19 = append((*dataPtr).Games19, game)

	}
}

func newgame(dataPtr *DataStructPrototype, nCells string) string {
	var newGame *GameSesh
	gameFound := false

	ncells_u64, err := strconv.ParseUint(nCells, 10, 32)

	if err != nil {
		return "Serve Error := 715\nCells (" + nCells + ") must be unsigned integer"
	}

	ncells_u8 := uint8(ncells_u64)
	// Get correct game slice and handle error
	games, err := dataPtr.getSlice(ncells_u8)

	if err != nil {
		return "Serve error := 925\nInvalid number of cells : " + string(ncells_u8) + "\nMust be 9, 13 or 19"
	}
	// Search for one player games
	for i := 0; i < len(games); i++ {
		if (*games[i]).NPlayers == 1 {
			(*games[i]).NPlayers = 2
			(*games[i]).Status = 0
			newGame = games[i]
			// Indicate game was found
			gameFound = true
			break
		}
	}

	if gameFound == false {
		// If no games with one player found create new one
		validID := false
		var ID uint32
		for !validID {
			validID = true
			ID = rand.Uint32()
			for _, game := range games {
				if ID == (*game).GameId {
					validID = false
					break
				}
			}
		}
		newGame = &(GameSesh{GameId: ID, NCells: ncells_u8, NPlayers: 1, Turn: 1, Status: 2, MessageT: ""})
		dataPtr.appendToSlice(newGame)
	}

	m, err := json.Marshal(newGame)
	if err != nil {
		return "Serve Error := 815"
	}
	return string(m)
}

func getgameinfo(dataPtr *DataStructPrototype, rawInfo string) string {
	gameUD := new(GameSesh)
	err := json.Unmarshal(json.RawMessage(rawInfo), gameUD)

	if err != nil {
		return "Serve Error := 816\n Invalid Game Structure (" + rawInfo + ")"
	}

	games, err := dataPtr.getSlice((*gameUD).NCells)
	if err != nil {
		return "Serve error := 925\nInvalid number of cells : " + string((*gameUD).NCells) + "\nMust be 9, 13 or 19"
	}

	var getgame *GameSesh
	for _, game := range games {
		if (*gameUD).GameId == (*game).GameId {
			getgame = game
			break
		}
	}

	if getgame == nil {
		return "gameid (" + string((*gameUD).GameId) + ") not found"
	} else {
		m, err := json.Marshal(getgame)
		if err != nil {
			return "Serve Error := 815"
		}
		return string(m)
	}
}

func postgameinfo(dataPtr *DataStructPrototype, rawInfo string) string {
	gameUD := new(GameSesh)
	err := json.Unmarshal(json.RawMessage(rawInfo), gameUD)

	if err != nil {
		return "Serve Error := 816\n Invalid Game Structure (" + rawInfo + ")"
	}

	games, err := dataPtr.getSlice((*gameUD).NCells)
	if err != nil {
		return "Serve error := 925\nInvalid number of cells : " + string((*gameUD).NCells) + "\nMust be 9, 13 or 19"
	}

	for i := 0; i < len(games); i++ {
		if (*games[i]).GameId == (*gameUD).GameId {
			*games[i] = *gameUD

			m, err := json.Marshal(gameUD)
			if err != nil {
				return "Serve Error := 815"
			}
			return string(m)
		}
	}
	return "Serve Error := 915"
}

func terminategame(dataPtr *DataStructPrototype, rawInfo string) string {
	gameUD := new(GameSesh)
	err := json.Unmarshal(json.RawMessage(rawInfo), gameUD)

	if err != nil {
		return "Serve Error := 816\n Invalid Game Structure (" + rawInfo + ")"
	}

	games, err := dataPtr.getSlice((*gameUD).NCells)
	if err != nil {
		return "Serve error := 925\nInvalid number of cells : " + string((*gameUD).NCells) + "\nMust be 9, 13 or 19"
	}

	for i := 0; i < len(games); i++ {
		if (*games[i]).GameId == (*gameUD).GameId {
			if (*games[i]).NPlayers == 2 {
				// Take final message from end of game
				(*games[i]).MessageT = (*gameUD).MessageT

				// Avoid breaking Nplayers
				(*games[i]).NPlayers--

				// Set status to end of game
				(*games[i]).Status = 1

				m, err := json.Marshal(*games[i])
				if err != nil {
					return "Serve Error := 815"
				}
				return string(m)

			} else {
				// Remove from slice when last player successfully quit
				dataPtr.removeFromSlice(gameUD.NCells, i)

				return "Game successfully removed"
			}

		}
	}
	return "Serve Error := 915"

}

func (dataPtr *DataStructPrototype) ServeHTTP(w http.ResponseWriter, r *http.Request) {

	request := strings.SplitN(r.URL.Path[1:], "/", 2)

	switch request[0] {
	case "new":
		fmt.Fprintf(w, "new game request\n")
		answer := newgame(dataPtr, request[1])
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