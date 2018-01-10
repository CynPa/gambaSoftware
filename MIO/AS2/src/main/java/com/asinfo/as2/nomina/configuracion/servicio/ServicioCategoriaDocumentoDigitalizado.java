package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCategoriaDocumentoDigitalizado
{
  public abstract List<CategoriaDocumentoDigitalizado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void guardar(CategoriaDocumentoDigitalizado paramCategoriaDocumentoDigitalizado);
  
  public abstract void eliminar(CategoriaDocumentoDigitalizado paramCategoriaDocumentoDigitalizado);
  
  public abstract CategoriaDocumentoDigitalizado buscarPorId(int paramInt);
  
  public abstract List<CategoriaDocumentoDigitalizado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado
 * JD-Core Version:    0.7.0.1
 */