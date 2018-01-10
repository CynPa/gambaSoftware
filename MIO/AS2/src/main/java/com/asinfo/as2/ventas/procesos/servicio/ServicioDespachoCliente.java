package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.DetalleDespachoCliente;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDespachoCliente
{
  public abstract void guardar(DespachoCliente paramDespachoCliente)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(DespachoCliente paramDespachoCliente)
    throws ExcepcionAS2;
  
  public abstract DespachoCliente buscarPorId(Integer paramInteger);
  
  public abstract DespachoCliente cargarDetalle(Integer paramInteger);
  
  public abstract List<DespachoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DespachoCliente> listaDespachosPorFacturar(Estado paramEstado, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List getReporteDespachoCliente(DespachoCliente paramDespachoCliente, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void esEditable(DespachoCliente paramDespachoCliente)
    throws ExcepcionAS2Financiero, ExcepcionAS2Ventas;
  
  public abstract void anular(DespachoCliente paramDespachoCliente, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2Ventas, ExcepcionAS2Inventario, AS2Exception;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract DespachoCliente cargarDetalleAFacturar(Integer paramInteger);
  
  public abstract void actualizarCantidadPorFacturar(List<Integer> paramList);
  
  public abstract DespachoCliente buscarPorFacturaCliente(Integer paramInteger);
  
  public abstract void actualizarInventarioProducto(DespachoCliente paramDespachoCliente)
    throws ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract void cargarDetalleDespachoArchivoTexto(TipoOrganizacion paramTipoOrganizacion, DespachoCliente paramDespachoCliente, InputStream paramInputStream, Bodega paramBodega)
    throws ExcepcionAS2;
  
  public abstract List<DespachoCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void actualizarAtributoEntidad(DespachoCliente paramDespachoCliente, HashMap<String, Object> paramHashMap);
  
  public abstract List<DespachoCliente> buscarDespachosNoFacturadosPorCliente(Integer paramInteger);
  
  public abstract List<DespachoCliente> obtenerDespachosPorFecha(Date paramDate, int paramInt, Empresa paramEmpresa);
  
  public abstract List<DetalleDespachoCliente> cargarDetalleDespachoClientePorDespacho(DespachoCliente paramDespachoCliente);
  
  public abstract void actualizarDespachoCliente(List<DetalleDespachoCliente> paramList, DespachoCliente paramDespachoCliente);
  
  public abstract List<DespachoCliente> autocompletarDespachosNoFacturadosPorCliente(Integer paramInteger, String paramString, int paramInt);
  
  public abstract List<DespachoCliente> obtenerDespachos(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract void contabilizar(List<DespachoCliente> paramList)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioDespachoCliente
 * JD-Core Version:    0.7.0.1
 */