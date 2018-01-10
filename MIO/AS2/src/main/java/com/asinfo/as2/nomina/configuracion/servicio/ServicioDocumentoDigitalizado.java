package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.DocumentoDigitalizado;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoDigitalizado
{
  public abstract List<DocumentoDigitalizado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void guardar(DocumentoDigitalizado paramDocumentoDigitalizado);
  
  public abstract void eliminar(DocumentoDigitalizado paramDocumentoDigitalizado);
  
  public abstract DocumentoDigitalizado buscarPorId(int paramInt);
  
  public abstract DocumentoDigitalizado cargarDetalles(int paramInt);
  
  public abstract List<DocumentoDigitalizado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado
 * JD-Core Version:    0.7.0.1
 */