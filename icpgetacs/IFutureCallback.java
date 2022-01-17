package icpgetacs;

public interface IFutureCallback {


    void onSuccess(String dir);

    void onError(Exception exception);
}
