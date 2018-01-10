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
/*  11:    */ import javax.validation.constraints.Max;
/*  12:    */ import javax.validation.constraints.Min;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="nivel_cuenta", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class NivelCuenta
/*  19:    */   extends EntidadBase
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="nivel_cuenta", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="nivel_cuenta")
/*  26:    */   @Column(name="id_nivel_cuenta")
/*  27:    */   private int idNivelCuenta;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="longitud", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int longitud;
/*  35:    */   @Column(name="codigo", nullable=false)
/*  36:    */   @Min(1L)
/*  37:    */   @Max(9L)
/*  38:    */   private int codigo;
/*  39:    */   @Column(name="nombre", length=50, nullable=false)
/*  40:    */   @Size(min=2, max=50)
/*  41:    */   @NotNull
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   
/*  51:    */   public NivelCuenta() {}
/*  52:    */   
/*  53:    */   public NivelCuenta(int idNivelCuenta, String nombre, int codigo)
/*  54:    */   {
/*  55: 74 */     this.idNivelCuenta = idNivelCuenta;
/*  56: 75 */     this.nombre = nombre;
/*  57: 76 */     this.codigo = codigo;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdNivelCuenta()
/*  61:    */   {
/*  62: 85 */     return this.idNivelCuenta;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdNivelCuenta(int idNivelCuenta)
/*  66:    */   {
/*  67: 95 */     this.idNivelCuenta = idNivelCuenta;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:104 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:114 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:123 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:133 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getLongitud()
/*  91:    */   {
/*  92:142 */     return this.longitud;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setLongitud(int longitud)
/*  96:    */   {
/*  97:152 */     this.longitud = longitud;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDescripcion()
/* 101:    */   {
/* 102:161 */     return this.descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDescripcion(String descripcion)
/* 106:    */   {
/* 107:171 */     this.descripcion = descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isActivo()
/* 111:    */   {
/* 112:180 */     return this.activo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setActivo(boolean activo)
/* 116:    */   {
/* 117:190 */     this.activo = activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isPredeterminado()
/* 121:    */   {
/* 122:199 */     return this.predeterminado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPredeterminado(boolean predeterminado)
/* 126:    */   {
/* 127:209 */     this.predeterminado = predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getId()
/* 131:    */   {
/* 132:214 */     return this.idNivelCuenta;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getCodigo()
/* 136:    */   {
/* 137:218 */     return this.codigo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setCodigo(int codigo)
/* 141:    */   {
/* 142:222 */     this.codigo = codigo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getNombre()
/* 146:    */   {
/* 147:226 */     return this.nombre;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setNombre(String nombre)
/* 151:    */   {
/* 152:230 */     this.nombre = nombre;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String toString()
/* 156:    */   {
/* 157:235 */     return this.nombre;
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.NivelCuenta
 * JD-Core Version:    0.7.0.1
 */