package util;

public class SyntaticError extends AnalysisError
{
    private Token token;

    public SyntaticError(String msg, int position, Token token)
	 {
        super(msg, position);
        this.token = token;
    }

    public SyntaticError(String msg)
    {
        super(msg);
    }

    public Token getToken() {
        return this.token;
    }
}
