package com.coviam.YoStore.EcomMerchants.controller;

import com.coviam.YoStore.EcomMerchants.dto.MerchantProductsDto;
import com.coviam.YoStore.EcomMerchants.dto.MerchantsDto;
import com.coviam.YoStore.EcomMerchants.entity.MerchantProducts;
import com.coviam.YoStore.EcomMerchants.entity.Merchants;
import com.coviam.YoStore.EcomMerchants.repository.MerchantsRepository;
import com.coviam.YoStore.EcomMerchants.service.MerchantsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/merchants")
@CrossOrigin("*")
public class MerchantsController {

    @Autowired
    MerchantsService merchantsService;

    @Autowired
    MerchantsRepository merchantsRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/putMerchant")
    public ResponseEntity<?> putMerchant(@RequestBody MerchantsDto merchantsDto)
    {
        Merchants merchants = new Merchants();
        BeanUtils.copyProperties(merchantsDto, merchants);
        merchantsService.insertMerchants(merchants);
        return new ResponseEntity<Merchants>(merchants, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/putMerchants")
    public ResponseEntity<?> putMerchants(@RequestBody List<Merchants> merchants)
    {
        //Merchants merchants = new Merchants();
        //BeanUtils.copyProperties(merchantsDto, merchants);
        merchantsRepository.insert(merchants);
        return new ResponseEntity<String>("Entries Ok.", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getMerchantsByProductSku/{array}")
    public ResponseEntity<?> getMerchantsByProductSku(@PathVariable("array") String array)
    {
        String[] arr = array.split("_");
        System.out.println(arr);
        String productSku = arr[0];
        String merchantId = arr[1];
        List<MerchantsDto> merchantsList = new ArrayList<>();
        for(int i = 1; i < arr.length; i++) {
            Merchants merchant = merchantsService.findByMerchantId(arr[i]);
            System.out.println(arr[i]);
            if (merchant == null) {
                return new ResponseEntity<String>("Merchant not found", HttpStatus.OK);
            }
            System.out.println(merchant);
            MerchantsDto merchantDto2 = new MerchantsDto();
            MerchantsDto merchantDto = new MerchantsDto();
            BeanUtils.copyProperties(merchant, merchantDto);
            merchantDto2.setCategory(merchantDto.getCategory());
            merchantDto2.setMerchantID(merchantDto.getMerchantID());
            merchantDto2.setQuantitySold(merchantDto.getQuantitySold());
            merchantDto2.setRating(merchantDto.getRating());
            List<MerchantProductsDto> merchantProductsDto = merchantDto.getMerchantProducts();
            List<MerchantProductsDto> mProdList = new ArrayList<>();
            System.out.println(merchantDto2);
            for (int j = 0; j < merchantProductsDto.size(); j++) {
                MerchantProductsDto merchantProductsDto1 = new MerchantProductsDto();
                BeanUtils.copyProperties(merchantProductsDto.get(j), merchantProductsDto1);
                if (merchantProductsDto1 != null && merchantProductsDto1.getProductId().equals(productSku) && merchantProductsDto1.getQuantity() != 0) {
                    mProdList.add(merchantProductsDto1);
                }
                else if (merchantProductsDto1 != null && merchantProductsDto1.getProductId().equals(productSku) && merchantProductsDto1.getQuantity() == 0){
                    continue;
                }
            }
            System.out.println(mProdList);
            merchantDto2.setMerchantProducts(mProdList);
            System.out.println(merchantDto2);
            merchantsList.add(merchantDto2);
            System.out.println(merchantsList);
        }
        return new ResponseEntity<List<MerchantsDto>>(merchantsList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateMerchantQuantity/{array}")
    public ResponseEntity<String> updateMerchantQuantity(@PathVariable("array") String array)
    {
        String[] arr = array.split("_");
        String mId = arr[0];
        String pSku = arr[1];
        int quantity = Integer.parseInt(arr[2]);
        Merchants merchant = merchantsService.findByMerchantId(mId);
        if(merchant == null)
        {
            return new ResponseEntity<String>("No Merchant found.", HttpStatus.OK);
        }
        Merchants merchantNew=new Merchants();
        BeanUtils.copyProperties(merchant,merchantNew);
        int q = merchantNew.getQuantitySold();
        List<MerchantProducts> merchantProducts = merchant.getMerchantProducts();
        List<MerchantProducts> mProdList = new ArrayList<>();
        for(int i=0;i<merchantProducts.size();i++)
        {
            MerchantProducts mP = new MerchantProducts();
            BeanUtils.copyProperties(merchantProducts.get(i),mP);
            if (mP!=null && mP.getProductId().equals(pSku) && mP.getQuantity()>=quantity){
                merchantNew.setQuantitySold((q+quantity));
                int qty = mP.getQuantity();
                System.out.println(qty);
                mP.setQuantity((qty-quantity));
                System.out.println(mP.getQuantity());
            }
            System.out.println(mP.getQuantity());
            mProdList.add(mP);
        }
        merchantNew.setMerchantProducts(mProdList);
        merchantsService.updateMerchant(merchantNew);
        return new ResponseEntity<String>("Order placed successfully", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllMerchants")
    public ResponseEntity<List<Merchants>> getAllMerchants()
    {
        List<Merchants> merchants=merchantsService.findAll();
        MerchantsDto merchantsDto=new MerchantsDto();
        if(merchants==null)
        {
            return null;
        }
        return new ResponseEntity<List<Merchants>>(merchants, HttpStatus.OK);

    }
}
