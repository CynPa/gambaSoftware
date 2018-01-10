package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioDocumentoRemote
{
  public abstract void guardar(Documento paramDocumento)
    throws AS2Exception;
  
  public abstract void eliminar(Documento paramDocumento);
  
  public abstract Documento buscarPorId(Integer paramInteger);
  
  public abstract List<Documento> buscarPorDocumentoBase(DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2;
  
  public abstract List<Documento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Documento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Documento cargarDetalle(int paramInt);
  
  public abstract Documento cargarSecuencia(Documento paramDocumento, PuntoDeVenta paramPuntoDeVenta, Date paramDate)
    throws ExcepcionAS2;
  
  public abstract AutorizacionDocumentoSRI cargarDocumentoConAutorizacion(Documento paramDocumento, PuntoDeVenta paramPuntoDeVenta, Date paramDate)
    throws ExcepcionAS2;
  
  public abstract List<Documento> buscarPorDocumentoBase(DocumentoBase paramDocumentoBase, int paramInt)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioDocumentoRemote
 * JD-Core Version:    0.7.0.1
 */