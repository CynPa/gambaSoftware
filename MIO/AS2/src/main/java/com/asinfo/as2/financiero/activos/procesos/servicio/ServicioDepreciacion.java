package com.asinfo.as2.financiero.activos.procesos.servicio;

import com.asinfo.as2.entities.ActivoFijo;
import com.asinfo.as2.entities.Depreciacion;
import com.asinfo.as2.entities.DetalleDepreciacion;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDepreciacion
{
  public abstract void guardar(Depreciacion paramDepreciacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(Depreciacion paramDepreciacion);
  
  public abstract Depreciacion buscarPorId(int paramInt);
  
  public abstract List<Depreciacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Depreciacion cargarDetalle(int paramInt);
  
  public abstract void generarListaDepreciacion(Depreciacion paramDepreciacion, int paramInt);
  
  public abstract void generarListaDepreciacion(Depreciacion paramDepreciacion);
  
  public abstract Depreciacion obtenerDepreciacionActivo(int paramInt, boolean paramBoolean);
  
  public abstract void esEditable(Depreciacion paramDepreciacion)
    throws ExcepcionAS2Financiero;
  
  public abstract Depreciacion generarListaRevalorizacion(Depreciacion paramDepreciacion)
    throws ExcepcionAS2Financiero;
  
  public abstract void contabilizar(Depreciacion paramDepreciacion)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract ActivoFijo generarDepreciacion(ActivoFijo paramActivoFijo);
  
  public abstract DetalleDepreciacion obtenerUltimoDetalleDepreciacionDepreciado(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacion
 * JD-Core Version:    0.7.0.1
 */