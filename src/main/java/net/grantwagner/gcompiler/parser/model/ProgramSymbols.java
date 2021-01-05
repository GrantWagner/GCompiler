package net.grantwagner.gcompiler.parser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.grantwagner.gcompiler.parser.model.symbols.FunctionSymbol;
import net.grantwagner.gcompiler.parser.model.symbols.StuctSymbol;
import net.grantwagner.gcompiler.parser.model.symbols.VariableSymbols;

@Data
public class ProgramSymbols {
  List<StuctSymbol> stucts = new ArrayList<>();
  List<FunctionSymbol> functions = new ArrayList<>();
  List<VariableSymbols> vars = new ArrayList<>();
  
  public void add(ProgramSymbols fileSymbols) {
    stucts.addAll(fileSymbols.getStucts());
    functions.addAll(fileSymbols.getFunctions());
    vars.addAll(fileSymbols.getVars());
  }
}
