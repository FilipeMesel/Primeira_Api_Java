package br.com.filipemesel.aluguelSalas.reuniao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@RestController
public class ReuniaoController {

    @Autowired
    private IReuniaoRepository reuniaoRepository;

    @ResponseBody
    @RequestMapping(path = "/reuniao", method = RequestMethod.POST)
    public ResponseEntity criarReuniao(@RequestBody Reuniao reuniao) throws ParseException
    {
        System.out.println("Solicitação POST recebida: " + reuniao);
        List<Reuniao> reunioes = reuniaoRepository.findAll();
        boolean isSalaLivre = true;

        for(Reuniao r : reunioes)
        {
            if (!reunioes.isEmpty()) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            
                Date dataReuniaoRequestInicial = sdf1.parse(reuniao.getDataInicial().toString());
                Date dataReuniaoRequestFinal = sdf1.parse(reuniao.getDataFinal().toString());
                Date dataRepositorioInicial = sdf2.parse(r.getDataInicial().toString());
                Date dataRepositorioFinal = sdf2.parse(r.getDataFinal().toString());
                
                
                System.out.println("dataReuniaoRequestInicial = " + dataReuniaoRequestInicial + " dataRepositorioInicial = " + dataRepositorioInicial);
                System.out.println("dataReuniaoRequestFinal = " + dataReuniaoRequestFinal + " dataRepositorioFinal = " + dataRepositorioFinal);

                /**
                 * inicial--I------------F--final
                 * I---inicial-------------final---F
                 * 
                 */
                if(r.getSala() == reuniao.getSala())
                {
                    if(dataReuniaoRequestInicial.equals(dataRepositorioInicial) && dataReuniaoRequestFinal.equals(dataRepositorioFinal) ||
                    (!(dataReuniaoRequestFinal.before(dataRepositorioInicial) || dataReuniaoRequestInicial.after(dataRepositorioFinal))))
                    {
                        isSalaLivre = false;
                        break;
                    }
                }
            }else {
                break;
            }
        }
        if(isSalaLivre == true)
        {
            reuniaoRepository.save(reuniao);
            return new ResponseEntity<>("Sala locada com sucesso", HttpStatus.OK);
        }else {
            System.out.println("Sala locada!");
            return new ResponseEntity<>("Sala locada nesta data", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(path = "/reuniao", method = RequestMethod.GET)
    public ResponseEntity listarReunioes()
    {
        List<Reuniao> reunioes = reuniaoRepository.findAll();
        return new ResponseEntity<>(reunioes, HttpStatus.OK);
    }

    @ResponseBody
    @Transactional
    @RequestMapping(path = "/reuniao/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletarReuniao(@PathVariable UUID id)
    {
        reuniaoRepository.deleteById(id);
        return new ResponseEntity<>("Deletado com sucesso!", HttpStatus.OK);
    }
    
}
