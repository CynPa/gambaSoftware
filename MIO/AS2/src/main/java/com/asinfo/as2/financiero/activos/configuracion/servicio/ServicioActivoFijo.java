package com.asinfo.as2.financiero.activos.configuracion.servicio;

import com.asinfo.as2.entities.ActivoFijo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioActivoFijo
{
  public abstract void guardar(ActivoFijo paramActivoFijo)
    throws ExcepcionAS2Financiero, ExcepcionAS2, Exception;
  
  public abstract void eliminar(ActivoFijo paramActivoFijo)
    throws ExcepcionAS2Financiero;
  
  public abstract ActivoFijo buscarPorId(int paramInt);
  
  public abstract List<ActivoFijo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ActivoFijo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ActivoFijo cargarDetalle(int paramInt);
  
  public abstract void esEditable(ActivoFijo paramActivoFijo);
  
  public abstract void contabilizar(ActivoFijo paramActivoFijo)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void cargarActivosFijos(InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract List<ActivoFijo> obtenerListaComboParaMantenimiento(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo
 * JD-Core Version:    0.7.0.1
 */