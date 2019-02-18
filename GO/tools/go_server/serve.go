package main


import( "net/http"
		"log"
		"fmt"
		"strings"
		"encoding/json"
)

 
type GameSesh struct{
    GameId int
	NPlayers int
	Turn int
	Status string
	MessageT string
}
 

type DataStructPrototype struct{
    Games []GameSesh
}

func newgame(dataPtr *DataStructPrototype) string{
	newGame := GameSesh{GameId: 1, NPlayers: 1, Turn: 1, Status: "Searching...", MessageT: ""}
	//*dataPtr.Games = append(*dataPtr.Games, newGame)
	m, err := json.Marshal(&newGame)
	if err == nil{
		return string(m)
	}
	return "Marshal error"
}
 

func (dataPtr *DataStructPrototype) ServeHTTP(w http.ResponseWriter, r *http.Request){

	request := strings.SplitN(r.URL.Path[1:],"/", 2)[0]

	switch request{
	case "new":
		fmt.Fprintf(w, "new game request\n")
		answer := newgame(dataPtr)
		fmt.Fprintf(w, answer)
		break
	case "terminate":
		fmt.Fprintf(w, "terminate game request\n")
		break
	case "get":
		fmt.Fprintf(w, "get game info request\n")
		break
	case "post":
		fmt.Fprintf(w, "post game info request\n")
		break
	default:
		w.WriteHeader(404)
		w.Write([]byte("404 biatch - " + http.StatusText(404)))
	}
	
}

 

func main() {
	http.Handle("/", new(DataStructPrototype))
    log.Fatal(http.ListenAndServe(":20020", nil))
}
