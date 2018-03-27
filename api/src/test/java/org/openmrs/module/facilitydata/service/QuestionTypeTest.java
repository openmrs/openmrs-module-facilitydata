package org.openmrs.module.facilitydata.service;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.facilitydata.model.CodedFacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.FacilityDataCodedOption;
import org.openmrs.module.facilitydata.model.FacilityDataQuestionType;
import org.openmrs.module.facilitydata.model.enums.DocumentType;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class QuestionTypeTest extends BaseModuleContextSensitiveTest {

    FacilityDataService service;

    public  QuestionTypeTest(){
         service = Context.getService(FacilityDataService.class);
    }

    @Test
    public void insert(){
        System.out.println("starting test");
        User user=new User();
        user.setId(1);
        CodedFacilityDataQuestionType codedFacilityDataQuestionType =new CodedFacilityDataQuestionType();
        codedFacilityDataQuestionType.setQuestionTypeId(1);
        codedFacilityDataQuestionType.setCreator(user);
        codedFacilityDataQuestionType.setDateCreated(new Date());
        codedFacilityDataQuestionType.setRetired(false);
        codedFacilityDataQuestionType.setName("gender");
        codedFacilityDataQuestionType.setUuid(UUID.randomUUID().toString());
        List<FacilityDataCodedOption> list =new ArrayList<FacilityDataCodedOption>();
        FacilityDataCodedOption codedOption1=new FacilityDataCodedOption();
        codedOption1.setOptionOrder(1);
        codedOption1.setDateCreated(new Date());
        codedOption1.setDescription("text");
        codedOption1.setCode("male");
        codedOption1.setName("male");
        codedOption1.setUuid(UUID.randomUUID().toString());
        codedOption1.setCreator(user);
        codedOption1.setRetired(false);
       // codedOption1.setQuestionType(codedFacilityDataQuestionType);

        FacilityDataCodedOption codedOption2=new FacilityDataCodedOption();
        codedOption2.setOptionOrder(2);
        codedOption2.setDateCreated(new Date());
        codedOption2.setDescription("text");
        codedOption2.setCode("male");
        codedOption2.setName("male");
        codedOption2.setUuid(UUID.randomUUID().toString());
        codedOption2.setCreator(user);
        codedOption2.setRetired(false);
      // codedOption2.setQuestionType(codedFacilityDataQuestionType);
        list.add(codedOption1);
        list.add(codedOption2);
        codedFacilityDataQuestionType.setOptions(list);
        service.saveQuestionType(codedFacilityDataQuestionType);
      //  System.out.println(codedFacilityDataQuestionType.getQuestionTypeId());
      //  System.out.println(codedFacilityDataQuestionType.getOptions().get(0).getId());
        System.out.println("**************************************");
       // System.out.println(service.getAllQuestionTypes().size());
       // System.out.println(((CodedFacilityDataQuestionType)service.getAllQuestionTypes().get(0)).getOptions().size());
        FacilityDataQuestionType questionType = service.getQuestionType(1);
        Assert.assertEquals(1,questionType.getQuestionTypeId().intValue());
      //  Assert.assertEquals(1,service.get);
        int size=        ((CodedFacilityDataQuestionType)questionType).getOptions().size();
       Assert.assertEquals(2,size);
    }


    @Test
    public void whenConvertedIntoEnum_thenGetsConvertedCorrectly() {

        String jsonValue = "JSON";
        DocumentType documentTypeEnum
                = DocumentType.valueOf(jsonValue);
        Assert.assertTrue(documentTypeEnum == DocumentType.JSON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenConvertedIntoEnum_thenThrowsException() {

        String jsonValue = "jsonP";
        DocumentType documentTypeEnum
                = DocumentType.valueOf(jsonValue);
    }
}
