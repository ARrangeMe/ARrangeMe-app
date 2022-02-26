package com.google.ar.sceneform.samples.src.services;

public class Constants {
    public static String jobsEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/jobs";
    public static String loginEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/users/login";
    public static String registerEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/users/register";
    public static String userEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/users/%s";
    public static String userJobsEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/users/%s/jobs";
    public static String itemsEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/items";
    public static String commitJobEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/jobs/%s/qr";
    public static String packJobEndpoint =
            "https://y4ff702tki.execute-api.us-east-2.amazonaws.com/prod/jobs/%s/pack/qr";
}
