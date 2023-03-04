import javafx.scene.image.*;

public class ImageCouple
{
  final String cheminAcces = "../code/Illustration/";
  public Piece piece;
  public ImageView ancien;
  public ImageView image;
  public ImageView info;
  public boolean placer = false;

  public ImageCouple(Piece piece_, ImageView image_)
  {
    piece  = piece_;
    ancien = image_;
    image  = createImg(piece_);
    info   = createIg(piece_); 
  }

  public ImageView createImg(Piece piece)
  {
    Image img = new Image(cheminAcces+piece.id+".png");
    ImageView ig = new ImageView();
    ig.setImage(img);
    ig.setFitWidth(95);
    ig.setFitHeight(95);
    return ig;
  }

  public ImageView createIg(Piece piece)
  {
    Image img = new Image(cheminAcces+piece.id+".png");
    ImageView ig = new ImageView();
    ig.setImage(img);
    return ig;
  }
}
