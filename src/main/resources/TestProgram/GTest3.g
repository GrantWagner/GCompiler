package net.grantwagner.gtest;

import System.String;
import System.IO.Log;
import System.Collections.List;
import System.OS.ResponseCodes


Number main(List<String> args) {
  Number numValue = -123o567i10;
  Boolean boolValue = true;
  Log.print(String.format("Hello World, the number value is %d, the boolean value is %b.", numValue, boolValue));
  return ResponseCodes.SUCCESS;
}
