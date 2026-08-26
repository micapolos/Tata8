package micapolos;

import micapolos.tata8.*;

class CaveStory {
  static int imageIndex;

  static void main() {
    Game.title = "Cave Story";

    var quoteImage = Game.loadImage(CaveStory.class, "quote.png");
    var curlyImage = Game.loadImage(CaveStory.class, "curly.png");

    var image = Game.newImage(3, 3);
    var canvas = image.newCanvas();
    canvas.drawPoint(1, 0);
    canvas.drawPoint(0, 1);
    canvas.drawPoint(1, 1);
    canvas.drawPoint(2, 1);
    canvas.drawPoint(1, 2);

    var cursorSprite = Game.newSprite();
    cursorSprite.image = image;
    cursorSprite.anchor.set(1, 1);

    var quoteSprite = Game.newSprite();
    quoteSprite.image = quoteImage;
    quoteSprite.position.set(0, 180);
    quoteSprite.anchor.set(quoteImage.size.width / 2f, quoteImage.size.height / 2f);

    Image[] images = quoteImage.slice(32, 1);

    Game.foregroundCanvas.fillRect(0, 200, 320, 1, Color.WHITE);
    Game.foregroundCanvas.fillRect(0, 201, 320, 55, Color.RED);

    Game.audio.volume = 0.5f;

    for (Channel channel : Game.audio.channels) {
      channel.wave = Wave.SAWTOOTH;
      Envelope envelope = channel.envelope;
      envelope.attack = 0;
      envelope.decay = 0.2f;
      envelope.sustain = 0.3f;
      envelope.release = 1f;
    }

    Game.onUpdate = () -> {
      if (Game.keys.z.didPress()) {
        quoteSprite.flip.x = !quoteSprite.flip.y;
        Game.backgroundCanvas.clear();
      }

      for (Channel channel : Game.audio.channels) {
        channel.sustain =
          Game.keys.left.isPressed() ||
              Game.keys.right.isPressed() ||
              Game.keys.up.isPressed() ||
              Game.keys.down.isPressed();
      }

      imageIndex++;
      if (imageIndex >= images.length) {
        imageIndex = 0;
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

      quoteSprite.position.set(Game.mouse.position.x, Game.mouse.position.y);
      cursorSprite.position.set(Game.mouse.position.x, Game.mouse.position.y);
      quoteSprite.isHidden = Game.mouse.isOutside;
      if (Game.mouse.button.isPressed()) {
        Game.backgroundCanvas.draw(quoteSprite);
      }

      quoteSprite.position.add(1, 0);
    };

    Game.start();
  }
}