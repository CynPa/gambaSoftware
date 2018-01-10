package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DetallePagoComision;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.PagoComision;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoComision
{
  public abstract void guardar(PagoComision paramPagoComision)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(PagoComision paramPagoComision);
  
  public abstract PagoComision buscarPorId(int paramInt);
  
  public abstract List<PagoComision> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PagoComision> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PagoComision cargarDetalle(int paramInt);
  
  public abstract List<DetallePagoComision> generarPagosComision(PagoComision paramPagoComision);
  
  public abstract List<Object[]> getReportePagoComision(PagoComision paramPagoComision);
  
  public abstract PagoComision obtenerUltimoPagoComision();
  
  public abstract List<Object[]> getReportePagoComision(int paramInt, Date paramDate1, Date paramDate2, EntidadUsuario paramEntidadUsuario, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, CategoriaEmpresa paramCategoriaEmpresa, Empresa paramEmpresa);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioPagoComision
 * JD-Core Version:    0.7.0.1
 */