package kz.alser.stepanov.semen.sellerassistant.API;

import kz.alser.stepanov.semen.sellerassistant.Models.LanguagesResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by semen.stepanov on 20.10.2016.
 */

public interface LanguagesAPI
{
    @GET ("/api/api.php?action=get_languages")
    Observable<LanguagesResponse> loadLanguages ();
}
