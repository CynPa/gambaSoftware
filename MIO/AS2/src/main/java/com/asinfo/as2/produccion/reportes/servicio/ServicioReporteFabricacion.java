package com.asinfo.as2.produccion.reportes.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.PersonaResponsable;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.ValorAtributo;
import com.asinfo.as2.entities.produccion.Maquina;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.entities.produccion.PlanProduccion;
import java.util.Date;
import java.util.List;

public abstract interface ServicioReporteFabricacion
{
  public abstract List getReporteOrdenSalidaMaterialvsConsumo(Date paramDate1, Date paramDate2, OrdenFabricacion paramOrdenFabricacion);
  
  public abstract List getReporteCostoFabricacion(Date paramDate1, Date paramDate2, OrdenFabricacion paramOrdenFabricacion);
  
  public abstract List getListaComboOrdenFabricacion(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract List getReporteParteProduccion(Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> getReporteProduccion(Date paramDate1, Date paramDate2, Bodega paramBodega, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Maquina paramMaquina, PersonaResponsable paramPersonaResponsable, List<ValorAtributo> paramList, boolean paramBoolean, int paramInt1, int paramInt2);
  
  public abstract List getReporteIRBPNR(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract List getReporteMateriales(PlanProduccion paramPlanProduccion);
  
  public abstract List<Object[]> getReporteTransformacionProducto(MovimientoInventario paramMovimientoInventario);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.servicio.ServicioReporteFabricacion
 * JD-Core Version:    0.7.0.1
 */