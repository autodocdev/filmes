package diones.filmes.com.filmes.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import diones.filmes.com.filmes.R;
import diones.filmes.com.filmes.model.entities.Movie;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.FilmeViewHolder> {

    private final List<Movie> mMovies;
    private Context mContext;

    public MoviesListAdapter(List<Movie> movies, Context context) {
        mMovies = movies;
        mContext = context;
    }

    @Override
    public FilmeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_movie, parent, false);

            return new FilmeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(FilmeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class FilmeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_movie_title)        TextView filmeTitleTextView;
        @Bind(R.id.item_movie_cover)        ImageView filmeThumbImageView;

        public FilmeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
