/*   1:    */ package com.asinfo.as2.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class NodoArbol<T>
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 1L;
/*  13:    */   private T valor;
/*  14:    */   private List<NodoArbol<T>> hijos;
/*  15:    */   private NodoArbol<T> padre;
/*  16:    */   private Map<String, Object> propiedades;
/*  17:    */   
/*  18:    */   public NodoArbol(T valor, NodoArbol<T> padre)
/*  19:    */   {
/*  20: 28 */     this.valor = valor;
/*  21: 29 */     this.padre = padre;
/*  22: 30 */     this.propiedades = new HashMap();
/*  23: 31 */     this.hijos = new ArrayList();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public T getValor()
/*  27:    */   {
/*  28: 35 */     return this.valor;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setValor(T valor)
/*  32:    */   {
/*  33: 39 */     this.valor = valor;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<NodoArbol<T>> getHijos()
/*  37:    */   {
/*  38: 43 */     if (this.hijos == null) {
/*  39: 44 */       this.hijos = new ArrayList();
/*  40:    */     }
/*  41: 46 */     return this.hijos;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setHijos(List<NodoArbol<T>> hijos)
/*  45:    */   {
/*  46: 50 */     this.hijos = hijos;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public NodoArbol<T> getPadre()
/*  50:    */   {
/*  51: 54 */     return this.padre;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setPadre(NodoArbol<T> padre)
/*  55:    */   {
/*  56: 58 */     this.padre = padre;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Map<String, Object> getPropiedades()
/*  60:    */   {
/*  61: 62 */     return this.propiedades;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setPropiedades(Map<String, Object> propiedades)
/*  65:    */   {
/*  66: 66 */     this.propiedades = propiedades;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addHijo(NodoArbol<T> nodo)
/*  70:    */   {
/*  71: 70 */     getHijos().add(nodo);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isHoja()
/*  75:    */   {
/*  76: 74 */     return getCantidadHijos() == 0;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getAltura()
/*  80:    */   {
/*  81: 78 */     if (isHoja()) {
/*  82: 79 */       return 1;
/*  83:    */     }
/*  84: 82 */     int maxAltura = 0;
/*  85: 83 */     for (int i = 0; i < getHijos().size(); i++)
/*  86:    */     {
/*  87: 85 */       NodoArbol<T> hijo = (NodoArbol)getHijos().get(i);
/*  88: 86 */       int hijoAltura = hijo.getAltura();
/*  89: 87 */       if (hijoAltura > maxAltura) {
/*  90: 88 */         maxAltura = hijoAltura;
/*  91:    */       }
/*  92:    */     }
/*  93: 91 */     return maxAltura + 1;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getPeso()
/*  97:    */   {
/*  98: 95 */     if (isHoja()) {
/*  99: 96 */       return 1;
/* 100:    */     }
/* 101: 98 */     int peso = 0;
/* 102: 99 */     for (int i = 0; i < getHijos().size(); i++) {
/* 103:101 */       peso += ((NodoArbol)this.hijos.get(i)).getPeso();
/* 104:    */     }
/* 105:103 */     return peso + 1;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getCantidadHojas()
/* 109:    */   {
/* 110:107 */     if (isHoja()) {
/* 111:108 */       return 1;
/* 112:    */     }
/* 113:110 */     int numHojas = 0;
/* 114:111 */     for (int i = 0; i < getHijos().size(); i++) {
/* 115:112 */       numHojas += ((NodoArbol)getHijos().get(i)).getCantidadHojas();
/* 116:    */     }
/* 117:114 */     return numHojas;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getCantidadHijos()
/* 121:    */   {
/* 122:118 */     return getHijos().size();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<NodoArbol<T>> getHojas()
/* 126:    */   {
/* 127:122 */     List<NodoArbol<T>> listaHojas = new ArrayList();
/* 128:123 */     if (isHoja())
/* 129:    */     {
/* 130:124 */       listaHojas.add(this);
/* 131:125 */       return listaHojas;
/* 132:    */     }
/* 133:127 */     for (int i = 0; i < getHijos().size(); i++) {
/* 134:128 */       listaHojas.addAll(((NodoArbol)getHijos().get(i)).getHojas());
/* 135:    */     }
/* 136:130 */     return listaHojas;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<NodoArbol<T>> getNoHojas()
/* 140:    */   {
/* 141:134 */     List<NodoArbol<T>> listaNoHojas = new ArrayList();
/* 142:135 */     if ((!isHoja()) && (this.padre != null)) {
/* 143:136 */       listaNoHojas.add(this);
/* 144:    */     }
/* 145:138 */     for (int i = 0; i < getHijos().size(); i++) {
/* 146:139 */       listaNoHojas.addAll(((NodoArbol)getHijos().get(i)).getNoHojas());
/* 147:    */     }
/* 148:141 */     return listaNoHojas;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public NodoArbol<T> buscar(T valor)
/* 152:    */   {
/* 153:145 */     if (valor.equals(this.valor)) {
/* 154:146 */       return this;
/* 155:    */     }
/* 156:148 */     if (isHoja()) {
/* 157:149 */       return null;
/* 158:    */     }
/* 159:152 */     for (int i = 0; i < getHijos().size(); i++)
/* 160:    */     {
/* 161:153 */       NodoArbol<T> aux = ((NodoArbol)getHijos().get(i)).buscar(valor);
/* 162:154 */       if (aux != null) {
/* 163:155 */         return aux;
/* 164:    */       }
/* 165:    */     }
/* 166:158 */     return null;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.NodoArbol
 * JD-Core Version:    0.7.0.1
 */