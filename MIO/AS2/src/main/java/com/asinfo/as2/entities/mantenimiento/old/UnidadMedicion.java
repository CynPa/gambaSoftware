/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoAtributo;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.EnumType;
/*   8:    */ import javax.persistence.Enumerated;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="unidad_medicion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class UnidadMedicion
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = -5154024749889672188L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="unidad_medicion", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="unidad_medicion")
/*  26:    */   @Column(name="id_unidad_medicion")
/*  27:    */   private int idUnidadMedicion;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", nullable=false, length=10)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="tipo_atributo", nullable=false)
/*  48:    */   @Enumerated(EnumType.ORDINAL)
/*  49:    */   private TipoAtributo tipoAtributo;
/*  50:    */   @Column(name="valores", nullable=false, length=200)
/*  51:    */   @NotNull
/*  52:    */   @Size(max=200)
/*  53:    */   private String valores;
/*  54:    */   
/*  55:    */   public UnidadMedicion() {}
/*  56:    */   
/*  57:    */   public UnidadMedicion(int idUnidadMedicion, String nombre)
/*  58:    */   {
/*  59:109 */     this.idUnidadMedicion = idUnidadMedicion;
/*  60:110 */     this.nombre = nombre;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdUnidadMedicion()
/*  64:    */   {
/*  65:122 */     return this.idUnidadMedicion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdUnidadMedicion(int idUnidadMedicion)
/*  69:    */   {
/*  70:132 */     this.idUnidadMedicion = idUnidadMedicion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:141 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:151 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:160 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:170 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getNombre()
/*  94:    */   {
/*  95:179 */     return this.nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNombre(String nombre)
/*  99:    */   {
/* 100:189 */     this.nombre = nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDescripcion()
/* 104:    */   {
/* 105:198 */     return this.descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setDescripcion(String descripcion)
/* 109:    */   {
/* 110:208 */     this.descripcion = descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isActivo()
/* 114:    */   {
/* 115:217 */     return this.activo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setActivo(boolean activo)
/* 119:    */   {
/* 120:227 */     this.activo = activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isPredeterminado()
/* 124:    */   {
/* 125:236 */     return this.predeterminado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPredeterminado(boolean predeterminado)
/* 129:    */   {
/* 130:246 */     this.predeterminado = predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getCodigo()
/* 134:    */   {
/* 135:255 */     return this.codigo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setCodigo(String codigo)
/* 139:    */   {
/* 140:265 */     this.codigo = codigo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public TipoAtributo getTipoAtributo()
/* 144:    */   {
/* 145:274 */     return this.tipoAtributo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setTipoAtributo(TipoAtributo tipoAtributo)
/* 149:    */   {
/* 150:284 */     this.tipoAtributo = tipoAtributo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getValores()
/* 154:    */   {
/* 155:293 */     return this.valores;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setValores(String valores)
/* 159:    */   {
/* 160:303 */     this.valores = valores;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getId()
/* 164:    */   {
/* 165:313 */     return this.idUnidadMedicion;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.UnidadMedicion
 * JD-Core Version:    0.7.0.1
 */