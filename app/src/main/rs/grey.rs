#pragma version(1)
#pragma rs java_package_name(de.maximal.effectcamera)


uchar4 __attribute__((kernel)) grey(uchar4 in, uint32_t x, uint32_t y) {

    in.r = in.r;
    in.g = in.r;
    in.b = in.r;

  return in;
}