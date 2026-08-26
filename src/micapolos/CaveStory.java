package micapolos;

import micapolos.tata8.*;

class CaveStory {
  static void main() {
    Game.title = "Cave Story";

    var quoteImage = Game.images[0];
    quoteImage.load("quote.png");

    var quoteSprite = Game.sprites[0];
    quoteSprite.image = quoteImage;
    quoteSprite.position.set(0, 180);
    quoteSprite.anchor.set(quoteImage.size.width() / 2, quoteImage.size.height() / 2);

    for (Sprite sprite : Game.sprites) {
      sprite.image = quoteImage;
      sprite.position.set(micapolos.tata8.Random.until(320), micapolos.tata8.Random.until(240));
    }

    Game.backgroundCanvas.fillRect(0, 200, 320, 1, Color.WHITE);
    Game.backgroundCanvas.fillRect(0, 201, 320, 55, Color.RED);

    Game.foregroundCanvas.fillRect(0, 0, 320, 8, Color.BLUE);
    Game.foregroundCanvas.fillRect(0, 8, 320, 1, Color.WHITE);

    Game.audio.volume = 0.5f;

    for (micapolos.tata8.Channel channel : Game.audio.channels) {
      channel.wave = Wave.SAWTOOTH;
      Envelope envelope = channel.envelope;
      envelope.attack = 0;
      envelope.decay = 0.2f;
      envelope.sustain = 0.3f;
      envelope.release = 1f;
    }
    ;

    Game.updater = () -> {
      if (Game.keys.z.didPress()) {
        quoteSprite.flip.x = !quoteSprite.flip.y;
      }

      for (micapolos.tata8.Channel channel : Game.audio.channels) {
        channel.sustain =
          Game.keys.left.isPressed() ||
              Game.keys.right.isPressed() ||
              Game.keys.up.isPressed() ||
              Game.keys.down.isPressed();
      }

      for (Sprite sprite : Game.sprites) {
        sprite.position.add(
            micapolos.tata8.Random.between(-2, 2),
            Random.between(-2, 2));
      }

      if (Game.keys.left.didPress()) {
        Game.audio.channels[0].play(Note.C_2);
      }

      if (Game.keys.up.didPress()) {
        Game.audio.channels[1].play(Note.C_2.plusSemitones(3));
      }

      if (Game.keys.down.didPress()) {
        Game.audio.channels[2].play(Note.C_2.plusSemitones(7));
      }

      if (Game.keys.right.didPress()) {
        Game.audio.channels[3].play(Note.C_2.plusSemitones(12));
      }

      if (Game.keys.x.isPressed()) {
        quoteSprite.angle += 15;
      }

      quoteSprite.position.add(1, 0);
    };
  }
}