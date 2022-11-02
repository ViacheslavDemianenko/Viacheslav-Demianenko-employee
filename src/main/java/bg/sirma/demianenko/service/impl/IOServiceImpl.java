package bg.sirma.demianenko.service.impl;

import bg.sirma.demianenko.service.IOService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor
public class IOServiceImpl implements IOService {
    @Override
    public void outputString(String s){
        System.out.println(s);
    }
}