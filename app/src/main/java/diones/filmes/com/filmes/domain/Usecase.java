package diones.filmes.com.filmes.domain;


import rx.Observable;

public interface Usecase<T> {
    Observable<T> execute();
}
