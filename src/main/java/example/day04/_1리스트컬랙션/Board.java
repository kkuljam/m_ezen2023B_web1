package example.day04._1리스트컬랙션;

public class Board {
    private String subhect;
    private String content;
    private String writer;

    public Board(String subhect, String content, String writer) {
        this.subhect = subhect;
        this.content = content;
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Board{" +
                "subhect='" + subhect + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }

    public String getSubhect() {
        return subhect;
    }

    public void setSubhect(String subhect) {
        this.subhect = subhect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
