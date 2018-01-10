/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="dispositivo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class Dispositivo
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="categoria_unidad_manejo", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_unidad_manejo")
/*  24:    */   @Column(name="id_categoria_unidad_manejo", unique=true, nullable=false)
/*  25:    */   private int idDispositivo;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=10)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=1, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=1, max=50)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", nullable=true, length=300)
/*  39:    */   @Size(max=300)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="ip", nullable=true, length=30)
/*  42:    */   @Size(max=30)
/*  43:    */   private String ip;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   @NotNull
/*  46: 66 */   private boolean activo = true;
/*  47:    */   @Column(name="predeterminado", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Column(name="fuera_linea", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private boolean fueraLinea;
/*  53:    */   
/*  54:    */   public Dispositivo() {}
/*  55:    */   
/*  56:    */   public Dispositivo(int idDispositivo, String codigo, String nombre)
/*  57:    */   {
/*  58: 88 */     this.idDispositivo = idDispositivo;
/*  59: 89 */     this.codigo = codigo;
/*  60: 90 */     this.nombre = nombre;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getId()
/*  64:    */   {
/*  65: 95 */     return this.idDispositivo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdDispositivo()
/*  69:    */   {
/*  70:104 */     return this.idDispositivo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdDispositivo(int idDispositivo)
/*  74:    */   {
/*  75:114 */     this.idDispositivo = idDispositivo;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80:123 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85:133 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdSucursal()
/*  89:    */   {
/*  90:142 */     return this.idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdSucursal(int idSucursal)
/*  94:    */   {
/*  95:152 */     this.idSucursal = idSucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getCodigo()
/*  99:    */   {
/* 100:161 */     return this.codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setCodigo(String codigo)
/* 104:    */   {
/* 105:171 */     this.codigo = codigo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getNombre()
/* 109:    */   {
/* 110:180 */     return this.nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setNombre(String nombre)
/* 114:    */   {
/* 115:190 */     this.nombre = nombre;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getDescripcion()
/* 119:    */   {
/* 120:199 */     return this.descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDescripcion(String descripcion)
/* 124:    */   {
/* 125:209 */     this.descripcion = descripcion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isActivo()
/* 129:    */   {
/* 130:218 */     return this.activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivo(boolean activo)
/* 134:    */   {
/* 135:228 */     this.activo = activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isPredeterminado()
/* 139:    */   {
/* 140:237 */     return this.predeterminado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPredeterminado(boolean predeterminado)
/* 144:    */   {
/* 145:247 */     this.predeterminado = predeterminado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getIp()
/* 149:    */   {
/* 150:251 */     return this.ip;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIp(String ip)
/* 154:    */   {
/* 155:255 */     this.ip = ip;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isFueraLinea()
/* 159:    */   {
/* 160:259 */     return this.fueraLinea;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setFueraLinea(boolean fueraLinea)
/* 164:    */   {
/* 165:263 */     this.fueraLinea = fueraLinea;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Dispositivo
 * JD-Core Version:    0.7.0.1
 */