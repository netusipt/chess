class Connection {

     constructor(host, port) {
          this.socket = new WebSocket("ws://" +  host + ":" + port)
     }
}