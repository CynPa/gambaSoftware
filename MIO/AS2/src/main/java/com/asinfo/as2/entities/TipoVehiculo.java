/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.OneToMany;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="tipo_vehiculo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class TipoVehiculo
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="tipo_vehiculo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_vehiculo")
/*  25:    */   @Column(name="id_tipo_vehiculo")
/*  26:    */   private int idTipoVehiculo;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @Size(min=2, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false)
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", nullable=false)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="tipoVehiculo")
/*  45: 54 */   private List<Ruta> listaRuta = new ArrayList();
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 59 */     return this.idTipoVehiculo;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public TipoVehiculo() {}
/*  53:    */   
/*  54:    */   public List<String> getCamposAuditables()
/*  55:    */   {
/*  56: 66 */     ArrayList<String> lista = new ArrayList();
/*  57: 67 */     lista.add("codigo");
/*  58: 68 */     lista.add("nombre");
/*  59: 69 */     lista.add("descripcion");
/*  60: 70 */     lista.add("activo");
/*  61:    */     
/*  62: 72 */     return lista;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TipoVehiculo(int idTipoVehiculo, String codigo, String nombre)
/*  66:    */   {
/*  67: 76 */     this.idTipoVehiculo = idTipoVehiculo;
/*  68: 77 */     this.codigo = codigo;
/*  69: 78 */     this.nombre = nombre;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdTipoVehiculo()
/*  73:    */   {
/*  74: 82 */     return this.idTipoVehiculo;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdTipoVehiculo(int idTipoVehiculo)
/*  78:    */   {
/*  79: 86 */     this.idTipoVehiculo = idTipoVehiculo;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84: 90 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89: 94 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94: 98 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:102 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getCodigo()
/* 103:    */   {
/* 104:106 */     return this.codigo;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setCodigo(String codigo)
/* 108:    */   {
/* 109:110 */     this.codigo = codigo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombre()
/* 113:    */   {
/* 114:114 */     return this.nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombre(String nombre)
/* 118:    */   {
/* 119:118 */     this.nombre = nombre;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getDescripcion()
/* 123:    */   {
/* 124:122 */     return this.descripcion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setDescripcion(String descripcion)
/* 128:    */   {
/* 129:126 */     this.descripcion = descripcion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isActivo()
/* 133:    */   {
/* 134:130 */     return this.activo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setActivo(boolean activo)
/* 138:    */   {
/* 139:134 */     this.activo = activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isPredeterminado()
/* 143:    */   {
/* 144:138 */     return this.predeterminado;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setPredeterminado(boolean predeterminado)
/* 148:    */   {
/* 149:142 */     this.predeterminado = predeterminado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public List<Ruta> getListaRuta()
/* 153:    */   {
/* 154:146 */     return this.listaRuta;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaRuta(List<Ruta> listaRuta)
/* 158:    */   {
/* 159:150 */     this.listaRuta = listaRuta;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String toString()
/* 163:    */   {
/* 164:155 */     return this.nombre;
/* 165:    */   }
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoVehiculo
 * JD-Core Version:    0.7.0.1
 */