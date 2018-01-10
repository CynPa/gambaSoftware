package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.MovimientoUnidadManejo;
import com.asinfo.as2.entities.SaldoUnidadManejo;
import com.asinfo.as2.entities.Subempresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.entities.UnidadManejo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMovimientoUnidadManejo
{
  public abstract void guardar(MovimientoUnidadManejo paramMovimientoUnidadManejo)
    throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception;
  
  public abstract List<MovimientoUnidadManejo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCirterio(Map<String, String> paramMap);
  
  public abstract void validarSaldoUnidadManejo(DetalleMovimientoUnidadManejo paramDetalleMovimientoUnidadManejo, boolean paramBoolean)
    throws ExcepcionAS2Inventario;
  
  public abstract List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int paramInt, Sucursal paramSucursal, Empresa paramEmpresa, Subempresa paramSubempresa, Transportista paramTransportista, UnidadManejo paramUnidadManejo);
  
  public abstract List<SaldoUnidadManejo> reporteSaldoUnidadManejo(int paramInt, Sucursal paramSucursal, Empresa paramEmpresa, Transportista paramTransportista);
  
  public abstract MovimientoUnidadManejo cargarDetalle(int paramInt);
  
  public abstract List<UnidadManejo> obtenerUnidadManejoPorUsuario(int paramInt, String paramString, Transportista paramTransportista, Sucursal paramSucursal, Empresa paramEmpresa, Subempresa paramSubempresa);
  
  public abstract void guardarRecepcion(MovimientoUnidadManejo paramMovimientoUnidadManejo)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void anular(MovimientoUnidadManejo paramMovimientoUnidadManejo)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int paramInt, Sucursal paramSucursal, UnidadManejo paramUnidadManejo);
  
  public abstract List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int paramInt, Empresa paramEmpresa, Subempresa paramSubempresa, UnidadManejo paramUnidadManejo);
  
  public abstract List<SaldoUnidadManejo> obtenerSaldoUnidadManejo(int paramInt, Transportista paramTransportista, UnidadManejo paramUnidadManejo);
  
  public abstract List<UnidadManejo> obtenerUnidadManejoPorUsuario(int paramInt, String paramString, Sucursal paramSucursal);
  
  public abstract List<UnidadManejo> obtenerUnidadManejoPorUsuario(int paramInt, String paramString, Empresa paramEmpresa, Subempresa paramSubempresa);
  
  public abstract List<UnidadManejo> obtenerUnidadManejoPorUsuario(int paramInt, String paramString, Transportista paramTransportista);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo
 * JD-Core Version:    0.7.0.1
 */