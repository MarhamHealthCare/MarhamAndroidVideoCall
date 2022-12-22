package com.marham.marhamvideocalllibrary.model.speciality;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

@Keep
public class NewAllSpecialitiesServerResponse extends ServerResponse {

    private NewAllSpecialities data;


    public NewAllSpecialities getData ()
    {
        return data;
    }

    public void setData (NewAllSpecialities data)
    {
        this.data = data;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", return_status = "+"]";
    }

}
