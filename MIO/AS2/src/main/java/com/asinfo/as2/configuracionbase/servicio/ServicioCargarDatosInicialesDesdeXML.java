package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.excepciones.ExcepcionAS2;
import javax.ejb.Local;

@Local
public abstract interface ServicioCargarDatosInicialesDesdeXML
{
  public abstract void cargarTipoIdentificacionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarOrganizacionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarUsuarioDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarModuloDesdeXML(boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract void cargarIdiomaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarUsuarioSucursalDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarRolDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarPermisoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarAccionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarConfiguracionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarCodigoFormaPagoSRIDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarFormaPagoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarSecuenciaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarTipoAsientoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarTipoComprobanteSRIDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarDocumentoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarCreditosTributariosSRIDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarConceptosRetencionSRIDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarCondicionPagoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarUnidadDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarMonedaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarCategoriaImpuestoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarImpuestoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarTipoCuentaBancariaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarTemaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarTipoBodegaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarBodegaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarListaPreciosDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarZonaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarCategoriaEmpresa()
    throws ExcepcionAS2;
  
  public abstract void cargarDocumentoVariableProcesoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarDocumentoContabilizacionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarPaisProvinciaCiudadDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarBancosDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarQuincenasDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarRubrosDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarFiltroProductoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarEstadoProcesoDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarHorasExtraDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarIBPClasificacionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarIBPMarcaDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarIBPCapacidadDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarIBPUnidadDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarsEstadoCivilDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void actualizarTipoIdentificacionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarEstadoChequeDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void actualizarConfiguracionConciliacionDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarReporteador()
    throws ExcepcionAS2;
  
  public abstract void actualizarTareaProgramada()
    throws ExcepcionAS2;
  
  public abstract void updateReportes(String paramString)
    throws ExcepcionAS2;
  
  public abstract void cargarTodosLosReportes();
  
  public abstract void cargarPlantillaXML()
    throws ExcepcionAS2;
  
  public abstract void actualizarVersionSistema();
  
  public abstract void cargarMotivoAjusteInventarioDesdeXML()
    throws ExcepcionAS2;
  
  public abstract void cargarOrigenIngresos()
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioCargarDatosInicialesDesdeXML
 * JD-Core Version:    0.7.0.1
 */