class Dj {

     loadSounds() {
          this.sounds["game_start"] = new Audio("data/audio/game_start.mp3");
          this.sounds["move"] = new Audio("data/audio/move.mp3");
          this.sounds["castling"] = new Audio("data/audio/castling.mp3");
          this.sounds["check"] = new Audio("data/audio/check.mp3");
          this.sounds["game_over"] = new Audio("data/audio/game_over.mp3");
     }

     playSound(type) {
          this.sounds[type].play();
     }
      
}