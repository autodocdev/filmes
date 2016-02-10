package diones.filmes.com.filmes.views;

import android.view.View;
import android.widget.ImageView;

public interface RecyclerClickListener {

    void onElementClick(int position, View sharedView, ImageView imageView);

}
