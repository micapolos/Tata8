import micapolos.synth.Channel;

void main() {
  Channel channel = new Channel();
  channel.reset();
  channel.env1.setAttack(1f);
  channel.env1.setDecay(1f);
  channel.env1.setRelease(2f);
  channel.note(24);
  channel.listen();
}
