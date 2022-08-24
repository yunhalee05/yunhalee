package com.yunhalee.flo;

import com.yunhalee.flo.layout.domain.LayoutRepository;
import com.yunhalee.flo.product.domain.ProductRepository;
import com.yunhalee.flo.product.service.ProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public abstract class ServiceTest {

    @MockBean
    protected ProductRepository productRepository;

    @MockBean
    protected LayoutRepository layoutRepository;

    @MockBean
    protected ProductService productService;
}
