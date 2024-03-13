package ezenweb.Service;

import ezenweb.model.dao.ProductDao;
import ezenweb.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private FileService fileService;
    public boolean postProductRegister(ProductDto productDto){
        System.out.println("ProductService.postProductRegister");
        // - 1. 첨부파일 업로드 처리
        List<String> list = new ArrayList<>();

        productDto.getUploadFiles().forEach( ( uploadFile ) -> {
            String fileName = fileService.fileUpload( uploadFile );
            if( fileName != null ) {  list.add( fileName ); }
        });

        productDto.setPimg( list );

        return productDao.postProductRegister(productDto);
    }
    public List<ProductDto> getProductList(){
        return productDao.getProductList();
    }
    //3. 해당 제품의 찜하기 등록
    public boolean getPlikeWrite(int pno,int mno){
        System.out.println("pno = " + pno);
        return productDao.getPlikeWrite(pno,mno);
    }
    //4. 해당 제품의 찜하기 상태 출력
    public boolean getPlikeView(int pno,int mno){
        return productDao.getPlikeView(pno,mno);
    }
    //5. 해당 제품의 찜하기 취소/삭제
    public boolean getPlikeDelete(int pno,int mno){
        return productDao.getPlikeDelete(pno,mno);
    }
}
