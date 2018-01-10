/*   1:    */ package com.asinfo.as2.rs.seguridad.dto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*   4:    */ import com.asinfo.as2.seguridad.modelo.Rol;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public class ConsultarUsuarioResponseDto
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private int idUsuario;
/*  13:    */   private String nombreUsuario;
/*  14:    */   private String nombre1;
/*  15:    */   private String nombre2;
/*  16:    */   private String clave;
/*  17:    */   private String email;
/*  18:    */   private TipoVisualizacionEnum tipoVisualizacion;
/*  19: 26 */   private List<Rol> listaRol = new ArrayList();
/*  20: 28 */   private List<OrganizacionResponseDto> listaOrganizacion = new ArrayList();
/*  21:    */   private Boolean indicadorAdministrador;
/*  22:    */   private int hashCode;
/*  23:    */   
/*  24:    */   public int getHashCode()
/*  25:    */   {
/*  26: 35 */     this.hashCode = hashCode();
/*  27: 36 */     return this.hashCode;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int hashCode()
/*  31:    */   {
/*  32: 41 */     int hash = 1;
/*  33: 42 */     hash += hash * 17 + (this.idUsuario + "").hashCode();
/*  34: 43 */     hash += hash * 22 + (this.nombreUsuario + "").hashCode();
/*  35: 44 */     hash += hash * 41 + (this.nombre1 + "").hashCode();
/*  36: 45 */     hash += hash * 41 + (this.nombre2 + "").hashCode();
/*  37: 46 */     hash += hash * 47 + (this.tipoVisualizacion + "").hashCode();
/*  38: 47 */     hash += hash * 57 + (this.clave + "").hashCode();
/*  39: 48 */     hash += hash * 20 + (this.indicadorAdministrador + "").hashCode();
/*  40: 49 */     hash += hash * 15 + (this.email + "").hashCode();
/*  41: 51 */     for (OrganizacionResponseDto organizacionResponseDto : this.listaOrganizacion) {
/*  42: 52 */       hash += 52 + (organizacionResponseDto != null ? organizacionResponseDto.hashCode() : 0);
/*  43:    */     }
/*  44: 55 */     return hash;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getIdUsuario()
/*  48:    */   {
/*  49: 62 */     return this.idUsuario;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setIdUsuario(int idUsuario)
/*  53:    */   {
/*  54: 70 */     this.idUsuario = idUsuario;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getNombreUsuario()
/*  58:    */   {
/*  59: 77 */     return this.nombreUsuario;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setNombreUsuario(String nombreUsuario)
/*  63:    */   {
/*  64: 85 */     this.nombreUsuario = nombreUsuario;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getNombre1()
/*  68:    */   {
/*  69: 92 */     return this.nombre1;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setNombre1(String nombre1)
/*  73:    */   {
/*  74:100 */     this.nombre1 = nombre1;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getNombre2()
/*  78:    */   {
/*  79:107 */     return this.nombre2;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setNombre2(String nombre2)
/*  83:    */   {
/*  84:115 */     this.nombre2 = nombre2;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<Rol> getListaRol()
/*  88:    */   {
/*  89:122 */     return this.listaRol;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setListaRol(List<Rol> listaRol)
/*  93:    */   {
/*  94:130 */     this.listaRol = listaRol;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<OrganizacionResponseDto> getListaOrganizacion()
/*  98:    */   {
/*  99:137 */     return this.listaOrganizacion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setListaOrganizacion(List<OrganizacionResponseDto> listaOrganizacion)
/* 103:    */   {
/* 104:145 */     this.listaOrganizacion = listaOrganizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public TipoVisualizacionEnum getTipoVisualizacion()
/* 108:    */   {
/* 109:149 */     return this.tipoVisualizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setTipoVisualizacion(TipoVisualizacionEnum tipoVisualizacion)
/* 113:    */   {
/* 114:153 */     this.tipoVisualizacion = tipoVisualizacion;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getClave()
/* 118:    */   {
/* 119:157 */     return this.clave;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setClave(String clave)
/* 123:    */   {
/* 124:161 */     this.clave = clave;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setHashCode(int hashCode)
/* 128:    */   {
/* 129:165 */     this.hashCode = hashCode;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Boolean getIndicadorAdministrador()
/* 133:    */   {
/* 134:169 */     return this.indicadorAdministrador;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setIndicadorAdministrador(Boolean indicadorAdministrador)
/* 138:    */   {
/* 139:173 */     this.indicadorAdministrador = indicadorAdministrador;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getEmail()
/* 143:    */   {
/* 144:177 */     return this.email;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setEmail(String email)
/* 148:    */   {
/* 149:181 */     this.email = email;
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.seguridad.dto.ConsultarUsuarioResponseDto
 * JD-Core Version:    0.7.0.1
 */