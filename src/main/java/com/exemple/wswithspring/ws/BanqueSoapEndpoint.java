package com.exemple.wswithspring.ws;

import com.exemple.wswithspring.entities.Compte;
import com.exemple.wswithspring.repositories.CompteRepository;
import com.exemple.wswithspring.soap.GetCompteRequest;
import com.exemple.wswithspring.soap.GetCompteResponse;
import com.exemple.wswithspring.soap.ListeCompteRequest;
import com.exemple.wswithspring.soap.ListeCompteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;
import java.util.List;

@Endpoint
public class BanqueSoapEndpoint {
    private final CompteRepository compteRepository;
    public static final String NAMESPACE_URI="http://www.exemple.com/wswithspring/soap";
    @Autowired
    public BanqueSoapEndpoint(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @ResponsePayload
    @PayloadRoot(localPart = "GetCompteRequest",namespace = NAMESPACE_URI)
    public GetCompteResponse consulterCompte(@RequestPayload GetCompteRequest getCompteRequest) throws DatatypeConfigurationException {
        GetCompteResponse response=new GetCompteResponse();
        Compte compteMetier=compteRepository.findById(getCompteRequest.getCode()).get();
        com.exemple.wswithspring.soap.Compte soapCompte=mapToSoapCompteFrom(compteMetier);
        response.setCompte(soapCompte);
        return response;
    }

    @ResponsePayload
    @PayloadRoot(localPart = "listeCompteRequest",namespace = NAMESPACE_URI)
    public ListeCompteResponse listCompte(@RequestPayload ListeCompteRequest listeCompteRequest){
        ListeCompteResponse response=new ListeCompteResponse();
        List<Compte> comptes=compteRepository.findAll();
        comptes.forEach(compte -> {
            com.exemple.wswithspring.soap.Compte soapCompte= null;
            try {
                soapCompte = mapToSoapCompteFrom(compte);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
            response.getComptes().add(soapCompte);
        });
        return response;
    }

    private com.exemple.wswithspring.soap.Compte mapToSoapCompteFrom(Compte compteMetier) throws DatatypeConfigurationException {
        com.exemple.wswithspring.soap.Compte soapCompte=new com.exemple.wswithspring.soap.Compte();
        soapCompte.setCode(compteMetier.getCode());
        soapCompte.setSolde(compteMetier.getSolde());
        soapCompte.setStatus(compteMetier.getStatus());

        GregorianCalendar gregorianCalendar=new GregorianCalendar();
        gregorianCalendar.setTime(compteMetier.getDateCreation());

        soapCompte.setDateCreation(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));

        return soapCompte;
    }


}
