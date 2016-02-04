package diones.filmes.com.filmes.domain;


import rx.Observable;

/**
 * Created by diones_xxx on 04/02/16.
 */
public interface Usecase<T> {
    Observable<T> execute();
}
