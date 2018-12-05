public class Parser {
  private Token curentToken;

  private accept (byte expectedKind) {
    if (this.currentToken.kind == expectedKind) {
      this.currentToken = scanner.scan();
    }
    else {
      // REPORTAR ERRO AQUI
    }
  }

  private void acceptIt () {
    this.curentToken = scanner.scan();
  }

  public void parse () {
    this.currentToken = scanner.scan();
    this.parseProgram();

    if (currentToken.kind != Token.DOT) {
      // REPORTAR ERRO AQUI
    }
  }

  private void parseIdentifier () {
    if (this.currentToken.kind == Token.ID) {
      this.acceptIt();
    }
    else {
      // REPORTAR ERRO AQUI
    }
  }
}
