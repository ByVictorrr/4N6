package com.example.digitalevidence.Helpers;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

public class CredentialHelper {
    static public CognitoCachingCredentialsProvider credentialsProvider;

    // Description: used to set the credentials for the activity your working in
    public CredentialHelper(Context context){
        this.credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-2:9bf3662b-58e0-440e-aaec-a03b002c8c4a", // Identity pool Id
                Regions.US_EAST_2 // Region
        );

    }
    public CognitoCachingCredentialsProvider getCredentialsProvider(){ return credentialsProvider;}

}
