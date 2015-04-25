package domain;

public class Result extends ClauseComponent {

    public String Vegetationfeature;
    public String Climatefeature;
    public byte[] VegetationPicture;

    public Result(String km, String vk, byte[] image) {
        Climatefeature = km;
        Vegetationfeature = vk;
        VegetationPicture = image;
    }

    public Result(String km, String vk) {
        Climatefeature = km;
        Vegetationfeature = vk;
    }

    public Result() {

    }
}
