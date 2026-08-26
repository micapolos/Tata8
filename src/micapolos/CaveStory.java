package micapolos;

import micapolos.tata8.*;

class CaveStory {
  static void main() {
    Game game = new Game();
    game.title = "Cave Story";

    var quoteImage = game.images[0];
    quoteImage.load("quote.png");

    var quoteSprite = game.sprites[0];
    quoteSprite.image = quoteImage;
    quoteSprite.position.set(0, 180);
    quoteSprite.anchor.set(quoteImage.size.width() / 2, quoteImage.size.height() / 2);

    for (Sprite sprite : game.sprites) {
      sprite.image = quoteImage;
      sprite.position.set(micapolos.tata8.Random.until(320), micapolos.tata8.Random.until(240));
    }

    game.backgroundCanvas.fillRect(0, 200, 320, 1, Color.WHITE);
    game.backgroundCanvas.fillRect(0, 201, 320, 55, Color.RED);

    game.foregroundCanvas.fillRect(0, 0, 320, 8, Color.BLUE);
    game.foregroundCanvas.fillRect(0, 8, 320, 1, Color.WHITE);

    game.audio.volume = 0.5f;

    for (micapolos.tata8.Channel channel : game.audio.channels) {
      channel.wave = Wave.SAWTOOTH;
      Envelope envelope = channel.envelope;
      envelope.attack = 0;
      envelope.decay = 0.2f;
      envelope.sustain = 0.3f;
      envelope.release = 1f;
    }
    ;

    game.updater = () -> {
      if (game.keys.z.didPress()) {
        quoteSprite.flip.x = !quoteSprite.flip.y;
      }

      for (micapolos.tata8.Channel channel : game.audio.channels) {
        channel.sustain =
          game.keys.left.isPressed() ||
              game.keys.right.isPressed() ||
              game.keys.up.isPressed() ||
              game.keys.down.isPressed();
      }

      for (Sprite sprite : game.sprites) {
        sprite.position.add(
            micapolos.tata8.Random.between(-2, 2),
            Random.between(-2, 2));
      }

      if (game.keys.left.didPress()) {
        game.audio.channels[0].play(Note.C_2);
      }

      if (game.keys.up.didPress()) {
        game.audio.channels[1].play(Note.C_2.plusSemitones(3));
      }

      if (game.keys.down.didPress()) {
        game.audio.channels[2].play(Note.C_2.plusSemitones(7));
      }

      if (game.keys.right.didPress()) {
        game.audio.channels[3].play(Note.C_2.plusSemitones(12));
      }

      if (game.keys.x.isPressed()) {
        quoteSprite.angle += 15;
      }

      quoteSprite.position.add(1, 0);
    };
  }
}