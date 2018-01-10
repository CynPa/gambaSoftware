/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.controller.LanguageController;
/*   8:    */ import com.asinfo.as2.controller.PageController;
/*   9:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*  10:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*  11:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  12:    */ import com.asinfo.as2.entities.Documento;
/*  13:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  14:    */ import com.asinfo.as2.entities.Organizacion;
/*  15:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  16:    */ import com.asinfo.as2.entities.Sucursal;
/*  17:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  18:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  19:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  20:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  21:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  22:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  23:    */ import com.asinfo.as2.util.AppUtil;
/*  24:    */ import com.asinfo.as2.utils.JsfUtil;
/*  25:    */ import java.io.BufferedInputStream;
/*  26:    */ import java.io.InputStream;
/*  27:    */ import java.util.HashMap;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.ejb.EJB;
/*  31:    */ import javax.faces.bean.ManagedBean;
/*  32:    */ import javax.faces.bean.ViewScoped;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.event.FileUploadEvent;
/*  35:    */ import org.primefaces.model.UploadedFile;
/*  36:    */ 
/*  37:    */ @ManagedBean
/*  38:    */ @ViewScoped
/*  39:    */ public class MigracionFacturaProveedorBean
/*  40:    */   extends PageController
/*  41:    */ {
/*  42:    */   private static final long serialVersionUID = -2279483837568577014L;
/*  43:    */   @EJB
/*  44:    */   private ServicioMigracion servicioMigracion;
/*  45:    */   @EJB
/*  46:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  47:    */   @EJB
/*  48:    */   private ServicioFacturaProveedor servicioFacturaProveedor;
/*  49:    */   @EJB
/*  50:    */   private ServicioDocumento servicioDocumento;
/*  51:    */   @EJB
/*  52:    */   private ServicioSecuencia servicioSecuencia;
/*  53:    */   @EJB
/*  54:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  55:    */   @EJB
/*  56:    */   private ServicioSucursal servicioSucursal;
/*  57:    */   
/*  58:    */   public String migrarFacturaProveedor(FileUploadEvent event)
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62: 90 */       String fileName = "migracion_factura_proveedor" + event.getFile().getFileName();
/*  63: 91 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  64: 92 */       List<FacturaProveedor> listaFacturaProveedor = this.servicioMigracion.migracionFacturaProveedor(fileName, input, 5, AppUtil.getOrganizacion()
/*  65: 93 */         .getId());
/*  66: 94 */       Map<Integer, Sucursal> mapaSucursales = new HashMap();
/*  67: 95 */       Map<Integer, PuntoDeVenta> mapaPuntoDeVenta = new HashMap();
/*  68: 96 */       Map<Integer, Documento> mapaDocumento = new HashMap();
/*  69: 97 */       for (FacturaProveedor facturaProveedor : listaFacturaProveedor) {
/*  70: 98 */         if (facturaProveedor.isIndicadorConRetencion())
/*  71:    */         {
/*  72: 99 */           facturaProveedor = this.servicioFacturaProveedor.cargarDetalle(Integer.valueOf(facturaProveedor.getIdFacturaProveedor()));
/*  73:100 */           FacturaProveedorSRI fpsri = this.servicioFacturaProveedorSRI.cargarDetalle(facturaProveedor.getFacturaProveedorSRI().getIdFacturaProveedorSRI());
/*  74:    */           
/*  75:102 */           Documento documento = (Documento)mapaDocumento.get(Integer.valueOf(fpsri.getDocumento().getIdDocumento()));
/*  76:103 */           if (documento == null)
/*  77:    */           {
/*  78:104 */             documento = this.servicioDocumento.cargarDetalle(fpsri.getDocumento().getIdDocumento());
/*  79:105 */             mapaDocumento.put(Integer.valueOf(documento.getId()), documento);
/*  80:    */           }
/*  81:107 */           PuntoDeVenta puntoVenta = (PuntoDeVenta)mapaPuntoDeVenta.get(Integer.valueOf(((AutorizacionDocumentoSRI)documento.getListaAutorizacionDocumentoSRI().get(0))
/*  82:108 */             .getPuntoDeVenta().getIdPuntoDeVenta()));
/*  83:109 */           if (puntoVenta == null)
/*  84:    */           {
/*  85:110 */             puntoVenta = this.servicioPuntoDeVenta.cargarDetalle(((AutorizacionDocumentoSRI)documento.getListaAutorizacionDocumentoSRI().get(0)).getPuntoDeVenta()
/*  86:111 */               .getIdPuntoDeVenta());
/*  87:112 */             mapaPuntoDeVenta.put(Integer.valueOf(puntoVenta.getId()), puntoVenta);
/*  88:    */           }
/*  89:115 */           Sucursal sucursal = (Sucursal)mapaSucursales.get(Integer.valueOf(puntoVenta.getSucursal().getIdSucursal()));
/*  90:116 */           if (sucursal == null)
/*  91:    */           {
/*  92:117 */             sucursal = this.servicioSucursal.cargarDetalle(puntoVenta.getSucursal().getIdSucursal());
/*  93:118 */             mapaSucursales.put(Integer.valueOf(sucursal.getId()), sucursal);
/*  94:    */           }
/*  95:120 */           fpsri.setEstablecimientoRetencion(sucursal.getCodigo());
/*  96:121 */           fpsri.setPuntoEmisionRetencion(puntoVenta.getCodigo());
/*  97:122 */           cargarSecuencia(fpsri, puntoVenta);
/*  98:123 */           this.servicioFacturaProveedorSRI.guardar(fpsri);
/*  99:    */         }
/* 100:    */       }
/* 101:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 102:    */     }
/* 103:    */     catch (AS2Exception e)
/* 104:    */     {
/* 105:131 */       JsfUtil.addErrorMessage(e, "");
/* 106:132 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:133 */       e.printStackTrace();
/* 108:    */     }
/* 109:    */     catch (ExcepcionAS2Financiero e)
/* 110:    */     {
/* 111:136 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 112:137 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 113:    */     }
/* 114:    */     catch (ExcepcionAS2Compras e)
/* 115:    */     {
/* 116:140 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 117:141 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 118:    */     }
/* 119:    */     catch (ExcepcionAS2 e)
/* 120:    */     {
/* 121:144 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 122:145 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 127:149 */       e.printStackTrace();
/* 128:    */     }
/* 129:151 */     return null;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void cargarSecuencia(FacturaProveedorSRI facturaProveedorSRI, PuntoDeVenta puntoDeVenta)
/* 133:    */     throws ExcepcionAS2
/* 134:    */   {
/* 135:155 */     AutorizacionDocumentoSRI autorizacionDocumentoSRI = null;
/* 136:156 */     if (puntoDeVenta != null) {
/* 137:157 */       autorizacionDocumentoSRI = this.servicioDocumento.cargarDocumentoConAutorizacion(facturaProveedorSRI.getDocumento(), puntoDeVenta, facturaProveedorSRI
/* 138:158 */         .getFechaEmisionRetencion());
/* 139:    */     }
/* 140:160 */     if ((Integer.parseInt(facturaProveedorSRI.getNumeroRetencion()) == 0) || (
/* 141:161 */       (facturaProveedorSRI.getDocumento() != null) && (facturaProveedorSRI.getDocumento().isIndicadorDocumentoElectronico()) && 
/* 142:162 */       (!facturaProveedorSRI.isTraCorregirDatos())))
/* 143:    */     {
/* 144:163 */       String numero = this.servicioSecuencia.obtenerSecuencia(facturaProveedorSRI.getDocumento().getSecuencia(), facturaProveedorSRI
/* 145:164 */         .getFechaEmisionRetencion());
/* 146:    */       
/* 147:166 */       facturaProveedorSRI.setNumeroRetencion(numero);
/* 148:167 */       facturaProveedorSRI.setAutorizacionRetencion(autorizacionDocumentoSRI.getAutorizacion());
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getRutaPlantilla()
/* 153:    */   {
/* 154:173 */     return "/resources/plantillas/compras/AS2 Factura Proveedor.xls";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getNombrePlantilla()
/* 158:    */   {
/* 159:178 */     return "AS2 Factura Proveedor.xls";
/* 160:    */   }
/* 161:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionFacturaProveedorBean
 * JD-Core Version:    0.7.0.1
 */