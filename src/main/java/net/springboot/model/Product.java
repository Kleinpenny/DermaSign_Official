package net.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String productName;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "skin_problem")
    private String skinProblem;

    @Column(name = "nursing_stage")
    private String nursingStage;

    @Column(name = "professional")
    private String professional;

    @Column(name = "description_en", length = 1000)
    private String descriptionEn;

    @Column(name = "description_de", length = 1000)
    private String descriptionDe;

    @Column(name = "ingredients_en", length = 1000)
    private String ingredientsEn;

    @Column(name = "ingredients_de", length = 1000)
    private String ingredientsDe;

    @Column(name = "volume")
    private String volume;

    @Column(name = "usage_en", length = 1000)
    private String usageEn;

    @Column(name = "usage_de", length = 1000)
    private String usageDe;

    @Column(name = "pic_name", length = 1000)
    private String picName;

    @Column(name = "benefit_en", length = 1000)
    private String benefitEn;

    @Column(name = "benefit_de", length = 1000)
    private String benefitDe;

    @Column(name = "suitable_en", length = 1000)
    private String suitableEn;

    @Column(name = "suitable_de", length = 1000)
    private String suitableDe;

    @Column(name = "dosage")
    private String dosage;

    public String getBenefitEn() {
        return this.benefitEn;
    }

    public void setBenefitEn(String benefitEn) {
        this.benefitEn = benefitEn;
    }

    public String getBenefitDe() {
        return this.benefitDe;
    }

    public void setBenefitDe(String benefitDe) {
        this.benefitDe = benefitDe;
    }

    public String getSuitableEn() {
        return this.suitableEn;
    }

    public void setSuitableEn(String suitableEn) {
        this.suitableEn = suitableEn;
    }

    public String getSuitableDe() {
        return this.suitableDe;
    }

    public void setSuitableDe(String suitableDe) {
        this.suitableDe = suitableDe;
    }

    public String getDosage() {
        return this.dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSkinProblem() {
        return this.skinProblem;
    }

    public void setSkinProblem(String skinProblem) {
        this.skinProblem = skinProblem;
    }

    public String getNursingStage() {
        return this.nursingStage;
    }

    public void setNursingStage(String nursingStage) {
        this.nursingStage = nursingStage;
    }

    public String getProfessional() {
        return this.professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getDescriptionEn() {
        return this.descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionDe() {
        return this.descriptionDe;
    }

    public void setDescriptionDe(String descriptionDe) {
        this.descriptionDe = descriptionDe;
    }

    public String getIngredientsEn() {
        return this.ingredientsEn;
    }

    public void setIngredientsEn(String ingredientsEn) {
        this.ingredientsEn = ingredientsEn;
    }

    public String getIngredientsDe() {
        return this.ingredientsDe;
    }

    public void setIngredientsDe(String ingredientsDe) {
        this.ingredientsDe = ingredientsDe;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getUsageEn() {
        return this.usageEn;
    }

    public void setUsageEn(String usageEn) {
        this.usageEn = usageEn;
    }

    public String getUsageDe() {
        return this.usageDe;
    }

    public void setUsageDe(String usageDe) {
        this.usageDe = usageDe;
    }

    public String getPicName() {
        return this.picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }
}