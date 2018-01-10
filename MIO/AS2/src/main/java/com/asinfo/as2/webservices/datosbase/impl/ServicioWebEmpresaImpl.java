/*  1:   */ package com.asinfo.as2.webservices.datosbase.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  4:   */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  7:   */ import com.asinfo.as2.webservices.datosbase.ServicioWebEmpresa;
/*  8:   */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  9:   */ import com.asinfo.pos.model.Cliente;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import java.util.HashMap;
/* 12:   */ import java.util.List;
/* 13:   */ import java.util.Map;
/* 14:   */ import javax.ejb.EJB;
/* 15:   */ import javax.jws.WebService;
/* 16:   */ 
/* 17:   */ @WebService(endpointInterface="com.asinfo.as2.webservices.datosbase.ServicioWebEmpresa")
/* 18:   */ public class ServicioWebEmpresaImpl
/* 19:   */   implements ServicioWebEmpresa
/* 20:   */ {
/* 21:   */   @EJB
/* 22:   */   private ServicioEmpresa servicioEmpresa;
/* 23:   */   
/* 24:   */   public boolean crearClienteConDatosBasicos(String identificacion, String codigoTipoIdentificacion, String nombre, String direccion, String telefono, int idOrganizacion, int idSucursal)
/* 25:   */     throws ExcepcionAS2Identification, ExcepcionAS2, Exception
/* 26:   */   {
/* 27:26 */     Map<String, String> filtros = new HashMap();
/* 28:27 */     filtros.put("identificacion", identificacion);
/* 29:28 */     boolean flag = false;
/* 30:   */     try
/* 31:   */     {
/* 32:30 */       this.servicioEmpresa.bucarEmpresaPorIdentificacion(filtros);
/* 33:   */     }
/* 34:   */     catch (ExcepcionAS2 e)
/* 35:   */     {
/* 36:   */       try
/* 37:   */       {
/* 38:33 */         this.servicioEmpresa.crearClienteConDatosBasicos(identificacion, codigoTipoIdentificacion, nombre, direccion, telefono, idOrganizacion, idSucursal, null);
/* 39:   */         
/* 40:35 */         flag = true;
/* 41:   */       }
/* 42:   */       catch (ExcepcionAS2Identification e1)
/* 43:   */       {
/* 44:37 */         System.out.println("Error al crear clientes con datos basicos por identificacion erronea >>" + e1);
/* 45:38 */         throw e1;
/* 46:   */       }
/* 47:   */       catch (ExcepcionAS2 e1)
/* 48:   */       {
/* 49:40 */         System.out.println("Error al crear clientes con datos basicos >>" + e1.getCodigoExcepcion());
/* 50:41 */         e1.printStackTrace();
/* 51:42 */         throw e1;
/* 52:   */       }
/* 53:   */     }
/* 54:45 */     return flag;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Cliente getCliente(String identificacion)
/* 58:   */     throws ExcepcionAS2
/* 59:   */   {
/* 60:53 */     Map<String, String> filters = new HashMap();
/* 61:54 */     filters.put("identificacion", identificacion);
/* 62:   */     
/* 63:56 */     Empresa empresa = this.servicioEmpresa.bucarEmpresaPorIdentificacion(filters);
/* 64:   */     
/* 65:58 */     Cliente cliente = new Cliente();
/* 66:59 */     cliente.setActivo(true);
/* 67:60 */     cliente.setDescripcion(empresa.getDescripcion());
/* 68:61 */     if (empresa.getDirecciones().size() > 0) {
/* 69:62 */       cliente.setDireccion1(((DireccionEmpresa)empresa.getDirecciones().get(0)).getDireccionCompleta());
/* 70:   */     } else {
/* 71:64 */       cliente.setDireccion1("");
/* 72:   */     }
/* 73:66 */     cliente.setEmail1(empresa.getEmail1());
/* 74:67 */     cliente.setIdentificacion(identificacion);
/* 75:68 */     cliente.setNombreComercial(empresa.getNombreComercial());
/* 76:69 */     cliente.setNombreFiscal(empresa.getNombreFiscal());
/* 77:70 */     cliente.setPredeterminado(false);
/* 78:71 */     if (empresa.getDirecciones().size() > 0) {
/* 79:72 */       cliente.setTelefono1(((DireccionEmpresa)empresa.getDirecciones().get(0)).getTelefono1());
/* 80:   */     } else {
/* 81:74 */       cliente.setTelefono1("");
/* 82:   */     }
/* 83:76 */     cliente.setTextoBusqueda(cliente.getNombreComercial() + " " + cliente.getNombreFiscal());
/* 84:   */     
/* 85:78 */     com.asinfo.pos.model.TipoIdentificacion tipoIdentificacionPos = new com.asinfo.pos.model.TipoIdentificacion();
/* 86:79 */     tipoIdentificacionPos.setIdTipoIdentificacionAs2(empresa.getTipoIdentificacion().getIdTipoIdentificacion());
/* 87:80 */     cliente.setTipoIdentificacion(tipoIdentificacionPos);
/* 88:   */     
/* 89:82 */     return cliente;
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.impl.ServicioWebEmpresaImpl
 * JD-Core Version:    0.7.0.1
 */