package kz.alser.stepanov.semen.sellerassistant.Models;

/**
 * Created by semen.stepanov on 15.09.2016.
 */
public class ResponseResult
{
    private Integer rspCode;

    private String rspMessage;

    public ResponseResult ()
    {
        this.rspCode = 0;
        this.rspMessage = "";
    }

    public ResponseResult (Integer rspCode, String rspMessage)
    {

        this.rspCode = rspCode;
        this.rspMessage = rspMessage;
    }

    public Integer getRspCode ()
    {
        return rspCode;
    }

    public void setRspCode (Integer rspCode)
    {
        this.rspCode = rspCode;
    }

    public String getRspMessage ()
    {
        return rspMessage;
    }

    public void setRspMessage (String rspMessage)
    {
        this.rspMessage = rspMessage;
    }
}
