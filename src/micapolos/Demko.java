import micapolos.tata8.Game;

int i3 = 10;
int i2 = i3;
int i4 = 1;
int i7 = 50;
int i8 = 30;
int i11 = 480;
int i13 = 256;
int i14 = 100;

void main() {
  Game.onUpdate = () -> {
    Game.background.canvas.clear();
    i2 += i4;
    int i6 = i2 + i7;
    Game.background.canvas.fillRect(i2, i6, i8, i8);
    int i10 = i11 - i2;
    int i12 = i13 - i14;
    Game.background.canvas.fillRect(i10, i12, i3, i3);
  };
  Game.start();
}