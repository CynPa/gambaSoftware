package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.ComponenteCosto;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.produccion.CostosDeFabricacion;
import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCostosDeFabricacion
{
  public abstract void generarCostosDeFabricacion(OrdenFabricacion paramOrdenFabricacion, CostosDeFabricacion paramCostosDeFabricacion);
  
  public abstract void generarCostosEstandar(Date paramDate1, Date paramDate2);
  
  public abstract List<CostosDeFabricacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CostosDeFabricacion buscarPorId(Integer paramInteger);
  
  public abstract void guardar(CostosDeFabricacion paramCostosDeFabricacion)
    throws AS2Exception;
  
  public abstract void eliminar(CostosDeFabricacion paramCostosDeFabricacion);
  
  public abstract BigDecimal getCostosMateriales(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract BigDecimal getCostosMateriales(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract void prorratearComponentesCosto(List<OrdenFabricacion> paramList, ComponenteCosto paramComponenteCosto, BigDecimal paramBigDecimal);
  
  public abstract void contabilizar(CostosDeFabricacion paramCostosDeFabricacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract CostosDeFabricacion cargarDetalle(Integer paramInteger);
  
  public abstract CostosDeFabricacion generarCostosDeFabricacion(CostosDeFabricacion paramCostosDeFabricacion, boolean paramBoolean)
    throws AS2Exception, ExcepcionAS2Financiero;
  
  public abstract void actualizarCantidadAsignadaMesCostofabricacion(DetalleCostoFabricacion paramDetalleCostoFabricacion);
  
  public abstract List<Object[]> getReporteCostosFabricacion(int paramInt1, Producto paramProducto, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioCostosDeFabricacion
 * JD-Core Version:    0.7.0.1
 */