package com.asinfo.as2.compras.importaciones.configuracion.servicio;

import com.asinfo.as2.entities.ProcesoImportacion;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioProcesoImportacion
{
  public abstract void guardar(ProcesoImportacion paramProcesoImportacion);
  
  public abstract void eliminar(ProcesoImportacion paramProcesoImportacion);
  
  public abstract ProcesoImportacion buscarPorId(Integer paramInteger);
  
  public abstract List<ProcesoImportacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ProcesoImportacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ProcesoImportacion buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioProcesoImportacion
 * JD-Core Version:    0.7.0.1
 */