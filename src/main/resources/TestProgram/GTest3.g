package net.grantwagner.gtest;

import System.String;
import System.IO.Log;
import System.Collections.List;
import System.OS.ResponseCodes

Number main(List<String> args) {
  Number value = -123o567i10;
  Log.print(String.format("Hello World, the number value is %d.", value));
  return ResponseCodes.SUCCESS;
}
